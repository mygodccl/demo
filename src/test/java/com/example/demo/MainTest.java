package com.example.demo;

import com.example.demo.orika.BO.PersonDestination;
import com.example.demo.orika.entity.PersonSource;
import com.example.demo.util.sift.ResultCell;
import com.example.demo.util.sift.ResultMap;
import com.example.demo.util.sift.SiftUtil;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.ScoringClassMapBuilder;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainTest {
    MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

    @Test
    public void simple(){
        MapperFactory factory = new DefaultMapperFactory.Builder()
                .classMapBuilderFactory(new ScoringClassMapBuilder.Factory())
                .build();
    }

    @Test
    public void sift(){
        PersonSource a = new PersonSource(1, "a");
        PersonSource b = new PersonSource(2, "b");
        PersonSource c = new PersonSource(3, "c");
        List<PersonSource> left = new ArrayList<>();
        left.add(a);
        left.add(b);
        left.add(c);

        PersonDestination a1 = new PersonDestination(1, "a");
        PersonDestination b1 = new PersonDestination(7, "b");
        PersonDestination c1 = new PersonDestination(3, "c");
        PersonDestination e = new PersonDestination(4, "e");
        PersonDestination f = new PersonDestination(5, "f");
        List<PersonDestination> right = new ArrayList<>();
        right.add(a1);
        right.add(b1);
        right.add(c1);
        right.add(e);
        right.add(f);

        SiftUtil.Processor<PersonSource, PersonDestination> processor = SiftUtil.buildProcess(left, right);
        ResultMap<PersonSource, PersonDestination> resultMap = processor.process((l, r) -> l.getId().equals(r.getId()));

        ResultCell<PersonSource, PersonDestination> difference = resultMap.getDifference();
        ResultCell<PersonSource, PersonDestination> intersection = resultMap.getIntersection();
        Collection<PersonSource> left1 = difference.getLeft();
        Collection<PersonDestination> right1 = difference.getRight();
        System.out.println(left1);
        System.out.println(right1);

        Collection<PersonSource> left2 = intersection.getLeft();
        Collection<PersonDestination> right2 = intersection.getRight();
        System.out.println(left2);
        System.out.println(right2);
    }
}
