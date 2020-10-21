package ru.school.matcha.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.school.matcha.domain.Form;
import ru.school.matcha.domain.User;
import ru.school.matcha.dto.FormDto;
import ru.school.matcha.dto.UserDto;
import ru.school.matcha.services.interfaces.CollectionConversionService;

import static java.util.Objects.isNull;

@Component
public class UserEntityToDtoConverter implements Converter<User, UserDto> {

    private final CollectionConversionService conversionService;

    @Autowired
    public UserEntityToDtoConverter(CollectionConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public UserDto convert(User source) {
        if (isNull(source)) {
            return null;
        }
        UserDto result = new UserDto();
        result.setUsername(source.getUsername());
        result.setFirstName(source.getFirstName());
        result.setLastName(source.getLastName());
        result.setEmail(source.getEmail());
        result.setGender(source.getGender());
        result.setBirthday(source.getBirthday());
        result.setDescription(source.getDescription());
        result.setIsActive(source.getIsActive());
        result.setForm(conversionService.convert(source.getForm(), FormDto.class));
        return result;
    }
}
