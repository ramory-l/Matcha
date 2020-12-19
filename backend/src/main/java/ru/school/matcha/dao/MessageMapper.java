package ru.school.matcha.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import ru.school.matcha.domain.Message;

import java.util.List;

@Mapper
public interface MessageMapper {

    void saveMessage(Message message);

    List<Message> getMessages(
            @Param("limit") int limit,
            @Param("offset") int offset,
            @Param("first") long first,
            @Param("second") long second
    );

    long getTotalCountMessages(@Param("first") long first, @Param("second") long second);

}
