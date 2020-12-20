package ru.school.matcha.converters;

import ru.school.matcha.domain.Message;
import ru.school.matcha.dto.MessageDto;

import static java.util.Objects.isNull;

public class MessageConverter extends Converter<MessageDto, Message> {

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
        return result;
    }

}
