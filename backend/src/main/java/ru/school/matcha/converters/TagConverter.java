package ru.school.matcha.converters;

import ru.school.matcha.domain.Tag;
import ru.school.matcha.dto.TagDto;

import static java.util.Objects.isNull;

public class TagConverter extends Converter<TagDto, Tag> {

    public TagConverter() {
        super(TagConverter::convertToEntity, TagConverter::convertToDto);
    }

    private static TagDto convertToDto(Tag source) {
        if (isNull(source)) {
            return null;
        }
        TagDto result = new TagDto();
        result.setId(source.getId());
        result.setName(source.getName());
        return result;
    }

    private static Tag convertToEntity(TagDto source) {
        if (isNull(source)) {
            return null;
        }
        Tag result = new Tag();
        result.setId(source.getId());
        result.setName(source.getName());
        return result;
    }

}
