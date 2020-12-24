package ru.school.matcha.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import ru.school.matcha.dao.GuestMapper;
import ru.school.matcha.domain.Guest;
import ru.school.matcha.exceptions.MatchaException;
import ru.school.matcha.services.interfaces.GuestService;
import ru.school.matcha.utils.MyBatisUtil;

import java.util.List;

import static java.util.Objects.nonNull;

@Slf4j
public class GuestServiceImpl implements GuestService {

    @Override
    public void createGuest(Long userId, Long guestId) {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            GuestMapper guestMapper = sqlSession.getMapper(GuestMapper.class);
            guestMapper.createGuest(userId, guestId);
            sqlSession.commit();
        } catch (Exception ex) {
            if (nonNull(sqlSession)) {
                sqlSession.rollback();
            }
            throw new MatchaException(String.format("Error to create guest(userId: %d; guestId: %d)", userId, guestId));
        } finally {
            if (nonNull(sqlSession)) {
                sqlSession.close();
            }
        }
    }

    @Override
    public List<Guest> getGuestsByUserId(Long userId) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            GuestMapper guestMapper = sqlSession.getMapper(GuestMapper.class);
            return guestMapper.getGuestsByUserId(userId);
        }
    }

}
