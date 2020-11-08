package ru.school.matcha.converters;

import ru.school.matcha.domain.Guest;
import ru.school.matcha.dto.GuestDto;

import static java.util.Objects.isNull;

public class GuestConverter extends Converter<GuestDto, Guest> {

    public GuestConverter() {
        super(GuestConverter::convertToEntity, GuestConverter::convertToDto);
    }

    private static GuestDto convertToDto(Guest source) {
        if (isNull(source)) {
            return null;
        }
        GuestDto result = new GuestDto();
        result.setUserId(source.getUserId());
        result.setDate(source.getDate());
        return result;
    }

    private static Guest convertToEntity(GuestDto source) {
        if (isNull(source)) {
            return null;
        }
        Guest result = new Guest();
        result.setUserId(source.getUserId());
        result.setDate(source.getDate());
        return result;
    }

}
