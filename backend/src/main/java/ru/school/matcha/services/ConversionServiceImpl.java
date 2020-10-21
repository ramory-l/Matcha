package ru.school.matcha.services;

import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.stereotype.Service;
import ru.school.matcha.services.interfaces.CollectionConversionService;

import java.util.Collection;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

@Lazy
@Service("conversionService")
public class ConversionServiceImpl extends DefaultConversionService implements CollectionConversionService {

    @Override
    public <R, A, T> R convertCollection(Collection<?> sourceCollection, Class<T> targetType, Collector<? super T, A, R> collector) {
        if (isNull(sourceCollection)) {
            return null;
        }
        Stream<T> convertedStream = sourceCollection.stream().map(o -> convert(o, targetType));
        return convertedStream.collect(collector);
    }
}
