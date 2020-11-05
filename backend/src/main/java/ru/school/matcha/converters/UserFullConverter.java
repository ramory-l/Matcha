package ru.school.matcha.converters;

import ru.school.matcha.domain.Form;
import ru.school.matcha.domain.User;
import ru.school.matcha.dto.FormDto;
import ru.school.matcha.dto.UserDto;
import ru.school.matcha.dto.UserFullDto;
import ru.school.matcha.enums.Gender;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class UserFullConverter extends Converter<UserFullDto, User> {

    public UserFullConverter() {
        super(UserFullConverter::convertToEntity, UserFullConverter::convertToDto);
    }

    private static UserFullDto convertToDto(User source) {
        if (isNull(source)) {
            return null;
        }
        UserFullDto result = new UserFullDto();
        result.setId(result.getId());
        result.setUsername(source.getUsername());
        result.setFirstName(source.getFirstName());
        result.setLastName(source.getLastName());
        result.setEmail(source.getEmail());
        result.setPassword(source.getPassword());
        result.setGender(source.getGender());
        result.setBirthday(source.getBirthday());
        result.setDescription(source.getDescription());
        Converter<FormDto, Form> formConverter = new FormConverter();
        result.setForm(formConverter.convertFromEntity(source.getForm()));
        result.setRate(source.getRate());
        return result;
    }

    private static User convertToEntity(UserFullDto source) {
        if (isNull(source)) {
            return null;
        }
        User result = new User();
        result.setId(source.getId());
        result.setUsername(source.getUsername());
        result.setFirstName(source.getFirstName());
        result.setLastName(source.getLastName());
        result.setEmail(source.getEmail());
        result.setPassword(source.getPassword());
        if (nonNull(source.getGender())) {
            Gender gender = null;
            if (source.getGender().equals("m")) {
                gender = Gender.MAN;
            } else if (source.getGender().equals("w")) {
                gender = Gender.WOMAN;
            }
            if (isNull(gender)) {
                result.setGender(null);
            } else {
                result.setGender(gender.getGender());
            }
        }
        result.setBirthday(source.getBirthday());
        result.setDescription(source.getDescription());
        Converter<FormDto, Form> formConverter = new FormConverter();
        result.setForm(formConverter.convertFromDto(source.getForm()));
        result.setRate(source.getRate());
        return result;
    }
}
