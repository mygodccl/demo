package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.example.demo.orika.BO.PersonDestination;
import com.example.demo.orika.entity.PersonSource;
import com.example.demo.util.sift.ResultMap;
import com.example.demo.util.sift.SiftUtil;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.ScoringClassMapBuilder;

public class MainTest {
    MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

    @Test
    public void simple() {
        MapperFactory factory = new DefaultMapperFactory.Builder().classMapBuilderFactory(new ScoringClassMapBuilder.Factory()).build();
    }

    @Test
    public void sift() {
        List<PersonSource> left = new ArrayList<>();
        PersonSource a = new PersonSource(1, "a");
        PersonSource b = new PersonSource(2, "b");
        PersonSource c = new PersonSource(3, "c");
        PersonSource g = new PersonSource(7, "g");
        PersonSource k = new PersonSource(8, "k");
        left.add(b);
        left.add(c);
        left.add(a);
        left.add(g);
        left.add(k);

        List<PersonDestination> right = new ArrayList<>();
        PersonDestination a1 = new PersonDestination(1, "a");
        PersonDestination b1 = new PersonDestination(2, "b");
        PersonDestination c1 = new PersonDestination(3, "c");
        right.add(a1);
        right.add(b1);
        right.add(c1);
        PersonDestination e = new PersonDestination(4, "e");
        PersonDestination f = new PersonDestination(5, "f");
        right.add(e);
        right.add(f);

        SiftUtil.Processor<PersonSource, PersonDestination> processor = SiftUtil.buildProcess(left, right);
        ResultMap<PersonSource, PersonDestination> resultMap = processor.process((l, r) -> l.getId().equals(r.getId()));

        System.out.println(1);
    }
}
