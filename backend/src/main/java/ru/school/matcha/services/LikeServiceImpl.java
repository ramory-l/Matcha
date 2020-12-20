package ru.school.matcha.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import ru.school.matcha.converters.Converter;
import ru.school.matcha.converters.LikeConverter;
import ru.school.matcha.domain.Like;
import ru.school.matcha.dto.LikeDto;
import ru.school.matcha.utils.MyBatisUtil;
import ru.school.matcha.dao.LikeMapper;
import ru.school.matcha.exceptions.MatchaException;
import ru.school.matcha.services.interfaces.LikeService;
import ru.school.matcha.services.interfaces.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
public class LikeServiceImpl implements LikeService {

    private static final Converter<LikeDto, Like> likeConverter = new LikeConverter();

    private static final UserService userService = new UserServiceImpl();

    @Override
    public void like(Long from, Long to, boolean isLike) {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            LikeMapper likeMapper = sqlSession.getMapper(LikeMapper.class);
            if (nonNull(likeMapper.getLike(from, to, isLike))) {
                if (isLike) {
                    throw new MatchaException("Like already exist");
                } else {
                    throw new MatchaException("Dislike already exist");
                }
            }
            checkUsers(from, to);
            likeMapper.like(from, to, isLike);
            if (isLike) {
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
    public List<Like> getLikesByUserId(Long userId, Boolean isLike, Boolean outgoing) {
        log.debug("Get likes/dislikes by user with id: {}", userId);
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            LikeMapper likeMapper = sqlSession.getMapper(LikeMapper.class);
            return likeMapper.getLikes(userId, isLike, outgoing);
        }
    }

    @Override
    public Map<String, List<Like>> getLikesByUserId(Long userId, Boolean outgoing) {
        log.debug("Get likes and dislikes by user with id: {}", userId);
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            LikeMapper likeMapper = sqlSession.getMapper(LikeMapper.class);
            Map<String, List<Like>> likes = new HashMap<>();
            likes.put("like", likeMapper.getLikes(userId, true, outgoing));
            likes.put("dislike", likeMapper.getLikes(userId, false, outgoing));
            return likes;
        }
    }

    @Override
    public void deleteLike(Long from, Long to, boolean isLike) {
        log.debug("Delete like/dislike from {} to {}", from, to);
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            LikeMapper likeMapper = sqlSession.getMapper(LikeMapper.class);
            if (isNull(likeMapper.getLike(from, to, isLike))) {
                if (isLike) {
                    throw new MatchaException("Like doesn't exist");
                } else {
                    throw new MatchaException("Dislike doesn't exist");
                }
            }
            checkUsers(from, to);
            likeMapper.deleteLike(from, to, isLike);
            if (isLike) {
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