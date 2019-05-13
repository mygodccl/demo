package com.example.demo.util.orika.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.example.demo.util.orika.mapper.MapperField.MapperField;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.ClassMap;
import ma.glasnost.orika.metadata.FieldMap;
import ma.glasnost.orika.metadata.MapperKey;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

public class GenMapper extends CustomMapper {

    private MapperFactory mapperFactory;

    private static GenMapper genMapper = new GenMapper();

    private Map<String, MapperField> fieldMappers = new HashMap<>();

    public GenMapper field(MapperField fieldMapper) {
        if (fieldMapper == null) {
            return genMapper;
        }
        Type[] types = getActualTypeArguments(fieldMapper);
        genMapper.fieldMappers.put(getFieldType(types[0], types[1]), fieldMapper);
        return genMapper;
    }

    public static GenMapper build(MapperFactory mapperFactory) {
        genMapper.mapperFactory = mapperFactory;
        return genMapper;
    }

    private GenMapper() {
    }

    @Override
    public void mapAtoB(Object source, Object destination, MappingContext context) {
        map(context, (aName, bName, aField, bField, fieldMapper) -> {
            Object v = fieldMapper.mapAtoBAction(aName, bName, aField.get(source), bField.get(destination));
            bField.set(destination, v);
        }, false);
    }

    @Override
    public void mapBtoA(Object destination, Object source, MappingContext context) {
        map(context, ((aName, bName, aField, bField, fieldMapper) -> {
            Object v = fieldMapper.mapBtoAAction(aName, bName, aField.get(source), bField.get(destination));
            aField.set(source, v);
        }), true);
    }

    private void map(MappingContext context, MapFunctional mapFunctional, boolean reverse) {
        if (fieldMappers.isEmpty()) {
            return;
        }
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

        for (FieldMap fieldMap : fieldsMapping) {
            MapperField fieldMapper = fieldMappers.get(getFieldType(fieldMap.getAType().getRawType(), fieldMap.getBType().getRawType()));
            if (fieldMapper != null) {
                try {
                    Field sourceField = sourceClass.getDeclaredField(fieldMap.getSourceExpression());
                    sourceField.setAccessible(true);
                    Field destField = destClass.getDeclaredField(fieldMap.getDestinationExpression());
                    destField.setAccessible(true);
                    mapFunctional.apply(fieldMap.getSourceExpression(), fieldMap.getDestinationExpression(), sourceField, destField, fieldMapper);
                } catch (IllegalAccessException | NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FunctionalInterface
    interface MapFunctional {
        void apply(String aName, String bName, Field aField, Field bField, MapperField fieldMapper) throws IllegalAccessException;
    }

    private static Type[] getActualTypeArguments(MapperField fieldMapper) {
        return ((ParameterizedTypeImpl) fieldMapper.getClass().getGenericInterfaces()[0]).getActualTypeArguments();
    }

    private static String getFieldType(Type a, Type b) {
        return a.toString() + "$" + b.toString();
    }
}
