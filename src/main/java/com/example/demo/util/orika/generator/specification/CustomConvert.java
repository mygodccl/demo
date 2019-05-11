package com.example.demo.util.orika.generator.specification;

import com.example.demo.util.orika.converter.HandleNullConverter;
import ma.glasnost.orika.converter.builtin.CopyByReferenceConverter;
import ma.glasnost.orika.impl.generator.SourceCodeContext;
import ma.glasnost.orika.impl.generator.VariableRef;
import ma.glasnost.orika.impl.generator.specification.Convert;
import ma.glasnost.orika.metadata.FieldMap;

import static ma.glasnost.orika.impl.generator.SourceCodeContext.statement;

public class CustomConvert extends Convert {
    @Override
    public String generateMappingCode(FieldMap fieldMap, VariableRef source, VariableRef destination, SourceCodeContext code) {
        String statement;
        boolean canHandleNulls;
        if (source.getConverter() instanceof CopyByReferenceConverter) {
            if (code.isDebugEnabled()) {
                code.debugField(fieldMap, "copying " + source.type() + " by reference");
            }
            statement = destination.assignIfPossible(source);
            canHandleNulls = true;
        } else {
            if (code.isDebugEnabled()) {
                code.debugField(fieldMap, "converting using " + source.getConverter());
            }
            statement = destination.assignIfPossible("%s.convert(%s, %s, mappingContext)", code.usedConverter(source.getConverter()),
                    source.asWrapper(), code.usedType(destination));
            canHandleNulls = false;
        }

        boolean shouldSetNull = shouldMapNulls(fieldMap, code) && !destination.isPrimitive();
        String destinationNotNull = destination.ifPathNotNull();

        if (!source.isNullPossible() || (canHandleNulls && shouldSetNull && "".equals(destinationNotNull))) {
            return statement(statement);
        } else {
            String elseSetNull = shouldSetNull ? (" else " + destinationNotNull + "{ \n" + destination.assignIfPossible("null")) + ";\n }"
                    : "";
            if (source.getConverter() instanceof HandleNullConverter) {
                return statement(statement);
            } else {
                return statement(source.ifNotNull() + "{ \n" + statement) + "\n}" + elseSetNull;
            }
        }
    }
}
