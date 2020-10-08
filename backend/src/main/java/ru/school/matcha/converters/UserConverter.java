package ru.school.matcha.converters;

import ru.school.matcha.domain.Form;
import ru.school.matcha.domain.User;
import ru.school.matcha.dto.FormDto;
import ru.school.matcha.dto.UserDto;
import ru.school.matcha.enums.Gender;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class UserConverter extends Converter<UserDto, User> {

    public UserConverter() {
        super(UserConverter::convertToEntity, UserConverter::convertToDto);
    }

    private static UserDto convertToDto(User source) {
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
        Converter<FormDto, Form> formConverter = new FormConverter();
        result.setForm(formConverter.convertFromEntity(source.getForm()));
        return result;
    }

    private static User convertToEntity(UserDto source) {
        if (isNull(source)) {
            return null;
        }
        User result = new User();
        result.setUsername(source.getUsername());
        result.setFirstName(source.getFirstName());
        result.setLastName(source.getLastName());
        result.setEmail(source.getEmail());
        if (nonNull(source.getGender())) {
            Gender gender = source.getGender().equals("m") ? Gender.MAN : Gender.WOMAN;
            result.setGender(gender.getGender());
        }
        result.setBirthday(source.getBirthday());
        result.setDescription(source.getDescription());
        return result;
    }

}
