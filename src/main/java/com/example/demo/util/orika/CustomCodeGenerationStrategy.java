package com.example.demo.util.orika;

import com.example.demo.util.orika.generator.specification.CustomConvert;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultCodeGenerationStrategy;
import ma.glasnost.orika.impl.generator.AggregateSpecification;
import ma.glasnost.orika.impl.generator.Specification;
import ma.glasnost.orika.impl.generator.specification.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CustomCodeGenerationStrategy extends DefaultCodeGenerationStrategy {
    private final List<Specification> specifications;
    private final List<AggregateSpecification> aggregateSpecifications;

    public CustomCodeGenerationStrategy() {
        this.specifications = new CopyOnWriteArrayList<>(
                Arrays.asList(
                        new ConvertArrayOrCollectionToArray(),
                        new ConvertArrayOrCollectionToCollection(),
                        new CustomConvert(),
                        new CopyByReference(),
                        new ApplyRegisteredMapper(),
                        new EnumToEnum(),
                        new StringToEnum(),
                        new UnmappableEnum(),
                        new ArrayOrCollectionToArray(),
                        new ArrayOrCollectionToCollection(),
                        new MapToMap(),
                        new MapToArray(),
                        new MapToCollection(),
                        new ArrayOrCollectionToMap(),
                        new StringToStringConvertible(),
                        new AnyTypeToString(),
                        new MultiOccurrenceElementToObject(),
                        new ObjectToMultiOccurrenceElement(),
                        new PrimitiveAndObject(),
                        new ObjectToObject()));

        this.aggregateSpecifications = new CopyOnWriteArrayList<>(
                Collections.singletonList(new MultiOccurrenceToMultiOccurrence()));
    }

    @Override
    public void setMapperFactory(MapperFactory mapperFactory) {
        for (Specification spec : this.specifications) {
            spec.setMapperFactory(mapperFactory);
        }
        for (AggregateSpecification spec : this.aggregateSpecifications) {
            spec.setMapperFactory(mapperFactory);
        }
    }

    @Override
    public void addSpecification(Specification spec, Position relativePosition, Class<? extends Specification> relativeSpec) {
        addSpec(this.specifications, spec, relativePosition, relativeSpec);
    }

    @Override
    public List<Specification> getSpecifications() {
        return specifications;
    }

    @Override
    public void addAggregateSpecification(AggregateSpecification spec, Position relativePosition, Class<AggregateSpecification> relativeSpec) {
        addSpec(this.aggregateSpecifications, spec, relativePosition, relativeSpec);
    }

    @Override
    public List<AggregateSpecification> getAggregateSpecifications() {
        return aggregateSpecifications;
    }
}
