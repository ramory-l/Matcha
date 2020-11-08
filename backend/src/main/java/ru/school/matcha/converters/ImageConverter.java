package ru.school.matcha.converters;

import ru.school.matcha.domain.Image;
import ru.school.matcha.dto.ImageDto;

import static java.util.Objects.isNull;

public class ImageConverter extends Converter<ImageDto, Image> {

    public ImageConverter() {
        super(ImageConverter::convertToEntity, ImageConverter::convertToDto);
    }

    private static ImageDto convertToDto(Image source) {
        if (isNull(source)) {
            return null;
        }
        ImageDto result = new ImageDto();
        result.setId(source.getId());
        result.setLink(source.getLink());
        result.setName(source.getName());
        result.setExternalId(source.getExternalId());
        result.setUserId(source.getUserId());
        return result;
    }

    private static Image convertToEntity(ImageDto source) {
        if (isNull(source)) {
            return null;
        }
        Image result = new Image();
        result.setId(source.getId());
        result.setLink(source.getLink());
        result.setName(source.getName());
        result.setExternalId(source.getExternalId());
        result.setUserId(source.getUserId());
        return result;
    }

}
