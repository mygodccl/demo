package com.example.demo.util.orika.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Set;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.ClassMap;
import ma.glasnost.orika.metadata.FieldMap;
import ma.glasnost.orika.metadata.MapperKey;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

public abstract class GenMapper<A, B> extends CustomMapper {

    protected abstract B mapAtoBAction(String aName, String bName, A aValue, B bValue);

    protected abstract A mapBtoAAction(String aName, String bName, A aValue, B bValue);

    private MapperFactory mapperFactory;

    GenMapper(MapperFactory mapperFactory) {
        this.mapperFactory = mapperFactory;
    }

    @Override
    public void mapAtoB(Object source, Object destination, MappingContext context) {
        map(context, ((aName, bName, aField, bField) -> {
            B v = mapAtoBAction(aName, bName, (A) aField.get(source), (B) bField.get(destination));
            bField.set(destination, v);
        }), false);
    }

    @Override
    public void mapBtoA(Object destination, Object source, MappingContext context) {
        map(context, ((aName, bName, aField, bField) -> {
            A v = mapBtoAAction(aName, bName, (A) aField.get(source), (B) bField.get(destination));
            aField.set(source, v);
        }), true);
    }

    private void map(MappingContext context, MapFunctional mapFunctional, boolean reverse) {
        Class<?> sourceClass;
        Class<?> destClass;
        if (reverse) {
            sourceClass = context.getResolvedDestinationType().getRawType();
            destClass = context.getResolvedSourceType().getRawType();
        } else {
            sourceClass = context.getResolvedSourceType().getRawType();
            destClass = context.getResolvedDestinationType().getRawType();
        }
        ClassMap<?, ?> classMap = mapperFactory.getClassMap(new MapperKey(context.getResolvedSourceType(), context.getResolvedDestinationType()));
        Set<FieldMap> fieldsMapping = classMap.getFieldsMapping();
        Type[] typeArguments = getActualTypeArguments();
        for (FieldMap fieldMap : fieldsMapping) {
            if (fieldMap.getAType().getRawType().equals(typeArguments[0]) && fieldMap.getBType().getRawType().equals(typeArguments[1])) {
                try {
                    Field sourceField = sourceClass.getDeclaredField(fieldMap.getSourceExpression());
                    sourceField.setAccessible(true);
                    Field destField = destClass.getDeclaredField(fieldMap.getDestinationExpression());
                    destField.setAccessible(true);
                    mapFunctional.apply(fieldMap.getSourceExpression(), fieldMap.getDestinationExpression(), sourceField, destField);
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FunctionalInterface
    interface MapFunctional {
        void apply(String aName, String bName, Field aField, Field bField) throws IllegalAccessException;
    }

    private Type[] getActualTypeArguments() {
        return ((ParameterizedTypeImpl) getClass().getGenericSuperclass()).getActualTypeArguments();
    }
}
