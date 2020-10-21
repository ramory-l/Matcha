package ru.school.matcha.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.school.matcha.domain.Form;
import ru.school.matcha.dto.FormDto;

import static java.util.Objects.isNull;

@Component
public class FormDtoToEntityConverter implements Converter<FormDto, Form> {

    @Override
    public Form convert(FormDto source) {
        if (isNull(source)) {
            return null;
        }
        Form result = new Form();
        result.setFlirt(source.isFlirt());
        result.setFriendship(source.isFriendship());
        result.setLove(source.isLove());
        result.setSex(source.isSex());
        result.setMan(source.isMan());
        result.setWoman(source.isWoman());
        return result;
    }
}
