package ru.school.matcha.converters;

import ru.school.matcha.domain.Image;
import ru.school.matcha.domain.Message;
import ru.school.matcha.dto.ImageDto;
import ru.school.matcha.dto.MessageDto;

import static java.util.Objects.isNull;

public class MessageConverter extends Converter<MessageDto, Message> {

    private static final Converter<ImageDto, Image> imageConverter = new ImageConverter();

    public MessageConverter() {
        super(MessageConverter::convertToEntity, MessageConverter::convertToDto);
    }

    private static MessageDto convertToDto(Message source) {
        if (isNull(source)) {
            return null;
        }
        MessageDto result = new MessageDto();
        result.setTo(source.getTo());
        result.setFrom(source.getFrom());
        result.setMessage(source.getMessage());
        result.setCreateTs(source.getCreateTs());
        result.setType(source.getType());
        result.setUsername(source.getUsername());
        result.setAvatar(imageConverter.convertFromEntity(source.getAvatar()));
        return result;
    }

    private static Message convertToEntity(MessageDto source) {
        if (isNull(source)) {
            return null;
        }
        Message result = new Message();
        result.setTo(source.getTo());
        result.setFrom(source.getFrom());
        result.setMessage(source.getMessage());
        result.setCreateTs(source.getCreateTs());
        result.setType(source.getType());
        result.setUsername(source.getUsername());
        result.setAvatar(imageConverter.convertFromDto(source.getAvatar()));
        return result;
    }

}
