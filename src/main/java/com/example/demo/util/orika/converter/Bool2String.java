package com.example.demo.util.orika.converter;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;

public class Bool2String extends HandleNullConverter<Boolean, String> {
    @Override
    public String convertTo(Boolean source, Type<String> destinationType, MappingContext mappingContext) {
        if (source == null) {
            return "N";
        }
        return source ? "Y" : "N";
    }

    @Override
    public Boolean convertFrom(String source, Type<Boolean> destinationType, MappingContext mappingContext) {
        return "Y".equals(source);
    }
}
