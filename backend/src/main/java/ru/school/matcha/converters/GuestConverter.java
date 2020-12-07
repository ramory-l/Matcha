package ru.school.matcha.converters;

import ru.school.matcha.domain.Guest;
import ru.school.matcha.domain.Image;
import ru.school.matcha.dto.GuestDto;
import ru.school.matcha.dto.ImageDto;

import static java.util.Objects.isNull;

public class GuestConverter extends Converter<GuestDto, Guest> {

    private static final Converter<ImageDto, Image> imageConverter;

    static {
        imageConverter = new ImageConverter();
    }

    public GuestConverter() {
        super(GuestConverter::convertToEntity, GuestConverter::convertToDto);
    }

    private static GuestDto convertToDto(Guest source) {
        if (isNull(source)) {
            return null;
        }
        GuestDto result = new GuestDto();
        result.setUsername(source.getUsername());
        result.setAvatar(imageConverter.convertFromEntity(source.getAvatar()));
        result.setDate(source.getDate());
        return result;
    }

    private static Guest convertToEntity(GuestDto source) {
        if (isNull(source)) {
            return null;
        }
        Guest result = new Guest();
        result.setUsername(source.getUsername());
        result.setAvatar(imageConverter.convertFromDto(source.getAvatar()));
        result.setDate(result.getDate());
        return result;
    }

}
