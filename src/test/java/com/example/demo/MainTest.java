package com.example.demo;

import org.junit.Test;

import com.example.demo.orika.BO.PersonDestination;
import com.example.demo.orika.BO.StringArr2String;
import com.example.demo.orika.entity.PersonSource;
import com.example.demo.util.orika.mapper.Boolean2StringMapper;

import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class MainTest {
    MapperFactory mapperFactory = new DefaultMapperFactory.Builder().mapNulls(false).useBuiltinConverters(true).build();

    @Test
    public void simple() {
        mapperFactory.classMap(PersonSource.class, PersonDestination.class).field("active1", "active2").byDefault()
                        .customize(new Boolean2StringMapper(mapperFactory)).register();
        mapperFactory.getConverterFactory().registerConverter(new StringArr2String());

        BoundMapperFacade<PersonSource, PersonDestination> facade = mapperFactory.getMapperFacade(PersonSource.class, PersonDestination.class);
        PersonDestination destination = facade.map(new PersonSource(1, "ccl", true, new String[] { "a", "b" }));
        // PersonSource personSource = facade.mapReverse(new PersonDestination(2, "dy", "Y"));
        System.out.println(destination);
        // System.out.println(personSource);
    }
}
