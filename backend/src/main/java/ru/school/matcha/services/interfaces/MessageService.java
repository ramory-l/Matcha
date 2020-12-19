package ru.school.matcha.services.interfaces;

import ru.school.matcha.domain.Message;

import java.util.List;

public interface MessageService {

    void saveMessage(Message message);

    List<Message> getMessages(int limit, int offset, long first, long second);

    long getTotalCountMessages(long first, long second);

}
