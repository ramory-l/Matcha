package ru.school.matcha.converters;

import ru.school.matcha.domain.Guest;
import ru.school.matcha.domain.User;
import ru.school.matcha.dto.GuestDto;
import ru.school.matcha.dto.UserWithMinimumInfoDto;

import static java.util.Objects.isNull;

public class GuestConverter extends Converter<GuestDto, Guest> {

    private static final Converter<UserWithMinimumInfoDto, User> userWithMinimumInfoConverter;

    static {
        userWithMinimumInfoConverter = new UserWithMinimumInfoConverter();
    }

    public GuestConverter() {
        super(GuestConverter::convertToEntity, GuestConverter::convertToDto);
    }

    private static GuestDto convertToDto(Guest source) {
        if (isNull(source)) {
            return null;
        }
        GuestDto result = new GuestDto();
        result.setUser(userWithMinimumInfoConverter.convertFromEntity(source.getUser()));
        result.setDate(source.getDate());
        return result;
    }

    private static Guest convertToEntity(GuestDto source) {
        if (isNull(source)) {
            return null;
        }
        Guest result = new Guest();
        result.setUser(userWithMinimumInfoConverter.convertFromDto(source.getUser()));
        result.setDate(source.getDate());
        return result;
    }

}
