package com.example.demo.util.orika.mapper.MapperField;

public interface MapperField<A, B> {
    B mapAtoBAction(String aName, String bName, A aValue, B bValue);

    A mapBtoAAction(String aName, String bName, A aValue, B bValue);
}
