package ru.school.matcha.converters;

import ru.school.matcha.domain.Image;
import ru.school.matcha.domain.User;
import ru.school.matcha.dto.*;

import static java.util.Objects.isNull;

public class UserMinInfoConverter extends Converter<UserMinInfoDto, User> {

    private static final Converter<ImageDto, Image> imageConverter;

    static {
        imageConverter = new ImageConverter();
    }

    public UserMinInfoConverter() {
        super(UserMinInfoConverter::convertToEntity, UserMinInfoConverter::convertToDto);
    }

    private static UserMinInfoDto convertToDto(User source) {
        if (isNull(source)) {
            return null;
        }
        UserMinInfoDto result = new UserMinInfoDto();
        result.setId(source.getId());
        result.setUsername(source.getUsername());
        result.setGender(source.getGender());
        result.setBirthday(source.getBirthday());
        result.setAvatar(imageConverter.convertFromEntity(source.getAvatar()));
        return result;
    }

    private static User convertToEntity(UserMinInfoDto source) {
        if (isNull(source)) {
            return null;
        }
        User result = new User();
        result.setId(source.getId());
        result.setUsername(source.getUsername());
        result.setGender(source.getGender());
        result.setBirthday(source.getBirthday());
        result.setAvatar(imageConverter.convertFromDto(source.getAvatar()));
        return result;
    }

}
