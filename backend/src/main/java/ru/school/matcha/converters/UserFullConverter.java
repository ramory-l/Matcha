package ru.school.matcha.converters;

import ru.school.matcha.domain.Form;
import ru.school.matcha.domain.Image;
import ru.school.matcha.domain.User;
import ru.school.matcha.dto.FormDto;
import ru.school.matcha.dto.ImageDto;
import ru.school.matcha.dto.UserFullDto;

import static java.util.Objects.isNull;

public class UserFullConverter extends Converter<UserFullDto, User> {

    private static final Converter<FormDto, Form> formConverter = new FormConverter();
    private static final Converter<ImageDto, Image> imageConverter = new ImageConverter();

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
        result.setForm(formConverter.convertFromEntity(source.getForm()));
        result.setRate(source.getRate());
        result.setIsActive(source.getIsActive());
        result.setAvatar(imageConverter.convertFromEntity(source.getAvatar()));
        result.setRole(source.getRole());
        result.setLatitude(source.getLatitude());
        result.setLongitude(source.getLongitude());
        result.setCreateTs(source.getCreateTs());
        result.setUpdateTs(source.getUpdateTs());
        result.setDeleteTs(source.getDeleteTs());
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
        result.setGender(source.getGender());
        result.setBirthday(source.getBirthday());
        result.setDescription(source.getDescription());
        result.setForm(formConverter.convertFromDto(source.getForm()));
        result.setRate(source.getRate());
        result.setIsActive(source.getIsActive());
        result.setRole(source.getRole());
        result.setLatitude(source.getLatitude());
        result.setLongitude(source.getLongitude());
        result.setCreateTs(source.getCreateTs());
        result.setUpdateTs(source.getUpdateTs());
        result.setAvatar(imageConverter.convertFromDto(source.getAvatar()));
        result.setDeleteTs(source.getDeleteTs());
        return result;
    }

}
