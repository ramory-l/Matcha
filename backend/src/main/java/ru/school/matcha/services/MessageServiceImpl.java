package ru.school.matcha.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import ru.school.matcha.dao.MessageMapper;
import ru.school.matcha.domain.Message;
import ru.school.matcha.exceptions.MatchaException;
import ru.school.matcha.services.interfaces.MessageService;
import ru.school.matcha.utils.MyBatisUtil;

import java.util.List;

import static java.util.Objects.nonNull;

@Slf4j
public class MessageServiceImpl implements MessageService {

    @Override
    public void saveMessage(Message message) {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            MessageMapper messageMapper = sqlSession.getMapper(MessageMapper.class);
            messageMapper.saveMessage(message);
            sqlSession.commit();
        } catch (Exception ex) {
            if (nonNull(sqlSession)) {
                sqlSession.rollback();
            }
            log.error(ex.getMessage());
            throw new MatchaException("Error to save message");
        } finally {
            if (nonNull(sqlSession)) {
                sqlSession.close();
            }
        }
    }

    @Override
    public List<Message> getMessages(int limit, int offset, long from, long to) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            MessageMapper messageMapper = sqlSession.getMapper(MessageMapper.class);
            return messageMapper.getMessages(limit, offset, from, to);
        }
    }

    @Override
    public long getTotalCountMessages(long first, long second) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            MessageMapper messageMapper = sqlSession.getMapper(MessageMapper.class);
            return messageMapper.getTotalCountMessages(first, second);
        }
    }

}
