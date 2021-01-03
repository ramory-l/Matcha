package ru.school.matcha.converters;

import ru.school.matcha.domain.Form;
import ru.school.matcha.dto.FormDto;

import static java.util.Objects.isNull;

public class FormConverter extends Converter<FormDto, Form> {

    public FormConverter() {
        super(FormConverter::convertToEntity, FormConverter::convertToDto);
    }

    private static FormDto convertToDto(Form source) {
        if (isNull(source)) {
            return null;
        }
        FormDto result = new FormDto();
        result.setId(source.getId());
        result.setMan(source.isMan());
        result.setWoman(source.isWoman());
        result.setAgeFrom(source.getAgeFrom());
        result.setAgeTo(source.getAgeTo());
        result.setRateFrom(source.getRateFrom());
        result.setRateTo(source.getRateTo());
        result.setRadius(source.getRadius());
        return result;
    }

    private static Form convertToEntity(FormDto source) {
        if (isNull(source)) {
            return null;
        }
        Form result = new Form();
        result.setId(source.getId());
        result.setMan(source.isMan());
        result.setWoman(source.isWoman());
        result.setAgeFrom(source.getAgeFrom());
        result.setAgeTo(source.getAgeTo());
        result.setRateFrom(source.getRateFrom());
        result.setRateTo(source.getRateTo());
        result.setRadius(source.getRadius());
        return result;
    }
}
