package ru.school.matcha.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.ConversionServiceFactory;
import ru.school.matcha.services.ConversionServiceImpl;

import java.util.Set;

@Configuration
public class ConverterConfiguration {

    @Bean("defaultConversionService")
    public ConversionService conversionServiceFactoryBean(ConversionServiceImpl conversionService, Set<Converter> conversion) {
        ConversionServiceFactory.registerConverters(conversion, conversionService);
        return conversionService;
    }
}
