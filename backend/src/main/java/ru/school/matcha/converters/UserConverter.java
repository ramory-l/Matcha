package ru.school.matcha.converters;

import ru.school.matcha.domain.Form;
import ru.school.matcha.domain.Image;
import ru.school.matcha.domain.User;
import ru.school.matcha.dto.FormDto;
import ru.school.matcha.dto.ImageDto;
import ru.school.matcha.dto.UserDto;

import static java.util.Objects.isNull;

public class UserConverter extends Converter<UserDto, User> {

    private static final Converter<FormDto, Form> formConverter;
    private static final Converter<ImageDto, Image> imageConverter;

    static {
        formConverter = new FormConverter();
        imageConverter = new ImageConverter();
    }

    public UserConverter() {
        super(UserConverter::convertToEntity, UserConverter::convertToDto);
    }

    private static UserDto convertToDto(User source) {
        if (isNull(source)) {
            return null;
        }
        UserDto result = new UserDto();
        result.setId(source.getId());
        result.setUsername(source.getUsername());
        result.setFirstName(source.getFirstName());
        result.setLastName(source.getLastName());
        result.setEmail(source.getEmail());
        result.setGender(source.getGender());
        result.setBirthday(source.getBirthday());
        result.setDescription(source.getDescription());
        result.setForm(formConverter.convertFromEntity(source.getForm()));
        result.setRate(source.getRate());
        result.setAvatar(imageConverter.convertFromEntity(source.getAvatar()));
        result.setLatitude(source.getLatitude());
        result.setLongitude(source.getLongitude());
        result.setLastLoginDate(source.getLastLoginDate());
        result.setIsOnline(source.getIsOnline());
        return result;
    }

    private static User convertToEntity(UserDto source) {
        if (isNull(source)) {
            return null;
        }
        User result = new User();
        result.setId(source.getId());
        result.setUsername(source.getUsername());
        result.setFirstName(source.getFirstName());
        result.setLastName(source.getLastName());
        result.setEmail(source.getEmail());
        result.setGender(source.getGender());
        result.setBirthday(source.getBirthday());
        result.setDescription(source.getDescription());
        result.setForm(formConverter.convertFromDto(source.getForm()));
        result.setRate(source.getRate());
        result.setAvatar(imageConverter.convertFromDto(source.getAvatar()));
        result.setLatitude(source.getLatitude());
        result.setLongitude(source.getLongitude());
        result.setLastLoginDate(source.getLastLoginDate());
        result.setIsOnline(source.getIsOnline());
        return result;
    }

}
