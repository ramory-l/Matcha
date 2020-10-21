package ru.school.matcha.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.school.matcha.domain.Form;
import ru.school.matcha.domain.User;
import ru.school.matcha.dto.UserDto;
import ru.school.matcha.services.interfaces.CollectionConversionService;

import static java.util.Objects.isNull;

@Component
public class UserDtoToEntityConverter implements Converter<UserDto, User> {

    private final CollectionConversionService conversionService;

    @Autowired
    public UserDtoToEntityConverter(CollectionConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public User convert(UserDto source) {
        if (isNull(source)) {
            return null;
        }
        User result = new User();
        result.setUsername(source.getUsername());
        result.setFirstName(source.getFirstName());
        result.setLastName(source.getLastName());
        result.setEmail(source.getEmail());
        result.setGender(source.getGender());
        result.setBirthday(source.getBirthday());
        result.setDescription(source.getDescription());
        result.setIsActive(source.getIsActive());
        result.setForm(conversionService.convert(source.getForm(), Form.class));
        return result;
    }
}
