package ru.school.matcha.converters;

import org.springframework.stereotype.Component;
import ru.school.matcha.domain.Form;
import ru.school.matcha.dto.FormDto;

import org.springframework.core.convert.converter.Converter;

import static java.util.Objects.isNull;

@Component
public class FormEntityToDtoConverter implements Converter<Form, FormDto> {

    @Override
    public FormDto convert(Form source) {
        if (isNull(source)) {
            return null;
        }
        FormDto result = new FormDto();
        result.setFlirt(source.isFlirt());
        result.setFriendship(source.isFriendship());
        result.setLove(source.isLove());
        result.setSex(source.isSex());
        result.setMan(source.isMan());
        result.setWoman(source.isWoman());
        return result;
    }
}
