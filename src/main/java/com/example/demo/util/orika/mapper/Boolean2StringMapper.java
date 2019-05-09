package com.example.demo.util.orika.mapper;

import com.example.demo.util.TextUtils;

import ma.glasnost.orika.MapperFactory;

public class Boolean2StringMapper extends GenMapper<Boolean, String> {

    public Boolean2StringMapper(MapperFactory mapperFactory) {
        super(mapperFactory);
    }

    @Override
    protected String mapAtoBAction(String aName, String bName, Boolean aValue, String bValue) {
        return TextUtils.boolText(aValue);
    }

    @Override
    protected Boolean mapBtoAAction(String aName, String bName, Boolean aValue, String bValue) {
        return TextUtils.parseBool(bValue);
    }
}
