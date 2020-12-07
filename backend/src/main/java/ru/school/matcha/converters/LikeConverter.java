package ru.school.matcha.converters;

import ru.school.matcha.domain.Form;
import ru.school.matcha.domain.Like;
import ru.school.matcha.dto.FormDto;
import ru.school.matcha.dto.LikeDto;

import static java.util.Objects.isNull;

public class LikeConverter extends Converter<LikeDto, Like> {

    public LikeConverter() {
        super(LikeConverter::convertToEntity, LikeConverter::convertToDto);
    }

    private static LikeDto convertToDto(Like source) {
        if (isNull(source)) {
            return null;
        }
        LikeDto result = new LikeDto();

        return result;
    }

    private static Like convertToEntity(LikeDto source) {
        if (isNull(source)) {
            return null;
        }
        Like result = new Like();

        return result;
    }

}