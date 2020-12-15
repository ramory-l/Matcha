package ru.school.matcha.converters;

import ru.school.matcha.domain.Image;
import ru.school.matcha.domain.Like;
import ru.school.matcha.dto.ImageDto;
import ru.school.matcha.dto.LikeDto;

import static java.util.Objects.isNull;

public class LikeConverter extends Converter<LikeDto, Like> {

    private static final Converter<ImageDto, Image> imageConverter;

    static {
        imageConverter = new ImageConverter();
    }

    public LikeConverter() {
        super(LikeConverter::convertToEntity, LikeConverter::convertToDto);
    }

    private static LikeDto convertToDto(Like source) {
        if (isNull(source)) {
            return null;
        }
        LikeDto result = new LikeDto();
        result.setId(source.getId());
        result.setUsername(source.getUsername());
        result.setAvatar(imageConverter.convertFromEntity(source.getAvatar()));
        return result;
    }

    private static Like convertToEntity(LikeDto source) {
        if (isNull(source)) {
            return null;
        }
        Like result = new Like();
        result.setId(source.getId());
        result.setUsername(source.getUsername());
        result.setAvatar(imageConverter.convertFromDto(source.getAvatar()));
        return result;
    }

}