package com.example.demo.orika.BO;

import org.apache.commons.lang3.StringUtils;

import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;

public class StringArr2String extends CustomConverter<String[], String> {
    @Override
    public String convert(String[] source, Type<? extends String> destinationType, MappingContext mappingContext) {
        return StringUtils.join(source, ",");
    }
}
