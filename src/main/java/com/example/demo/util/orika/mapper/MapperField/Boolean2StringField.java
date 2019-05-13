package com.example.demo.util.orika.mapper.MapperField;

public class Boolean2StringField implements MapperField<Boolean, String> {
    @Override
    public String mapAtoBAction(String aName, String bName, Boolean aValue, String bValue) {
        return Boolean.TRUE.equals(aValue) ? "Y" : "N";
    }

    @Override
    public Boolean mapBtoAAction(String aName, String bName, Boolean aValue, String bValue) {
        return "Y".equals(bValue);
    }
}
