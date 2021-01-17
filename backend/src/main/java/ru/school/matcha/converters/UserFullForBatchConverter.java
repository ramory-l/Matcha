package ru.school.matcha.converters;

import ru.school.matcha.domain.Form;
import ru.school.matcha.domain.Image;
import ru.school.matcha.domain.UserFullForBatch;
import ru.school.matcha.dto.FormDto;
import ru.school.matcha.dto.ImageDto;
import ru.school.matcha.dto.UserFullForBatchDto;

import static java.util.Objects.isNull;

public class UserFullForBatchConverter extends Converter<UserFullForBatchDto, UserFullForBatch> {

    private static final Converter<FormDto, Form> formConverter = new FormConverter();
    private static final Converter<ImageDto, Image> imageConverter = new ImageConverter();

    public UserFullForBatchConverter() {
        super(UserFullForBatchConverter::convertToEntity, UserFullForBatchConverter::convertToDto);
    }

    private static UserFullForBatchDto convertToDto(UserFullForBatch source) {
        if (isNull(source)) {
            return null;
        }
        UserFullForBatchDto result = new UserFullForBatchDto();
        result.setId(source.getId());
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
        result.setRole(source.getRole());
        result.setLatitude(source.getLatitude());
        result.setLongitude(source.getLongitude());
        result.setImage(imageConverter.convertFromEntity(source.getImage()));
        result.setLastLoginDate(source.getLastLoginDate());
        return result;
    }

    private static UserFullForBatch convertToEntity(UserFullForBatchDto source) {
        if (isNull(source)) {
            return null;
        }
        UserFullForBatch result = new UserFullForBatch();
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
        result.setImage(imageConverter.convertFromDto(source.getImage()));
        result.setLastLoginDate(source.getLastLoginDate());
        return result;
    }

}
