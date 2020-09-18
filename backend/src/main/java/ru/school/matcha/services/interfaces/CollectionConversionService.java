package ru.school.matcha.services.interfaces;

import org.springframework.core.convert.ConversionService;

import java.util.Collection;
import java.util.stream.Collector;

public interface CollectionConversionService extends ConversionService {
    <R, A, T> R convertCollection(Collection<?> sourceCollection, Class<T> targetType, Collector<? super T, A, R> collector);
}
