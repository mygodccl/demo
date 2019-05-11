package com.example.demo;

import com.example.demo.orika.BO.PersonDestination;
import com.example.demo.orika.entity.PersonSource;
import com.example.demo.util.orika.CustomCodeGenerationStrategy;
import com.example.demo.util.orika.converter.Bool2String;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.junit.Test;

public class MainTest {
    MapperFactory mapperFactory = new DefaultMapperFactory.Builder().codeGenerationStrategy(new CustomCodeGenerationStrategy()).mapNulls(false).build();

    @Test
    public void simple() {
        mapperFactory.getConverterFactory().registerConverter(new Bool2String());
        mapperFactory.classMap(PersonSource.class, PersonDestination.class).field("active1", "active2").
                byDefault()
                .register();

        BoundMapperFacade<PersonSource, PersonDestination> facade = mapperFactory.getMapperFacade(PersonSource.class, PersonDestination.class);
        PersonDestination destination = facade.map(new PersonSource(1, "ccl", true));
        PersonSource personSource = facade.mapReverse(new PersonDestination(2, "dy", null));
        System.out.println(destination);
        System.out.println(personSource);
    }
}
