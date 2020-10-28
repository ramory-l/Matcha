package ru.school.matcha.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import ru.school.matcha.configs.MyBatisUtil;
import ru.school.matcha.dao.LikeMapper;
import ru.school.matcha.exceptions.MatchaException;
import ru.school.matcha.services.interfaces.LikeService;
import ru.school.matcha.services.interfaces.UserService;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
public class LikeServiceImpl implements LikeService {

    private static final UserService userService;

    static {
        userService = new UserServiceImpl();
    }

    @Override
    public void like(Long from, Long to, boolean like) {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            LikeMapper likeMapper = sqlSession.getMapper(LikeMapper.class);
            if (nonNull(likeMapper.getLike(from, to, like))) {
                if (like) {
                    throw new MatchaException("Like already exist");
                } else {
                    throw new MatchaException("Dislike already exist");
                }
            }
            checkUsers(from, to);
            likeMapper.like(from, to, like);
            if (like) {
                likeMapper.addRate(to);
            } else {
                likeMapper.deleteRate(to);
            }
            sqlSession.commit();
        } catch (Exception ex) {
            if (nonNull(sqlSession)) {
                sqlSession.rollback();
            }
            throw new MatchaException("Error to like. " + ex.getMessage());
        } finally {
            if (nonNull(sqlSession)) {
                sqlSession.close();
            }
        }
    }

    @Override
    public List<Long> getLikes(Long id, Boolean like) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            LikeMapper likeMapper = sqlSession.getMapper(LikeMapper.class);
            return likeMapper.getLikes(id, like);
        }
    }

    @Override
    public void deleteLike(Long from, Long to, boolean like) {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            LikeMapper likeMapper = sqlSession.getMapper(LikeMapper.class);
            if (isNull(likeMapper.getLike(from, to, like))) {
                if (like) {
                    throw new MatchaException("Like doesn't exist");
                } else {
                    throw new MatchaException("Dislike doesn't exist");
                }
            }
            checkUsers(from, to);
            likeMapper.deleteLike(from, to, like);
            if (like) {
                likeMapper.deleteRate(to);
            } else {
                likeMapper.addRate(to);
            }
            sqlSession.commit();
        } catch (Exception ex) {
            if (nonNull(sqlSession)) {
                sqlSession.rollback();
            }
            throw new MatchaException("Error to delete like. " + ex.getMessage());
        } finally {
            if (nonNull(sqlSession)) {
                sqlSession.close();
            }
        }
    }

    private void checkUsers(Long from, Long to) {
        if (isNull(userService.getUserById(from))) {
            log.error("User 'from' with id: {} doesn't exist", from);
            throw new MatchaException("User 'from' with id: " + from + " doesn't exist");
        }
        if (isNull(userService.getUserById(to))) {
            log.error("User 'to' with id: {} doesn't exist", to);
            throw new MatchaException("User 'to' with id: " + to + " doesn't exist");
        }
    }

}