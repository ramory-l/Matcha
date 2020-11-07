package ru.school.matcha.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import ru.school.matcha.dao.TagMapper;
import ru.school.matcha.domain.Tag;
import ru.school.matcha.exceptions.MatchaException;
import ru.school.matcha.services.interfaces.TagService;
import ru.school.matcha.utils.MyBatisUtil;

import java.util.List;

import static java.util.Objects.nonNull;

@Slf4j
public class TagServiceImpl implements TagService {

    @Override
    public void createTag(Tag tag) {
        log.info("Create new tag");
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            TagMapper tagMapper = sqlSession.getMapper(TagMapper.class);
            tagMapper.createTag(tag);
            sqlSession.commit();
        } catch (Exception ex) {
            if (nonNull(sqlSession)) {
                sqlSession.rollback();
            }
            throw new MatchaException("Error to create tag");
        } finally {
            if (nonNull(sqlSession)) {
                sqlSession.close();
            }
        }
    }

    @Override
    public List<Tag> getTags() {
        log.info("Get tags");
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            TagMapper tagMapper = sqlSession.getMapper(TagMapper.class);
            return tagMapper.getTags();
        }
    }

    @Override
    public Tag getTagById(Long id) {
        log.info("Get tag by id: {}", id);
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            TagMapper tagMapper = sqlSession.getMapper(TagMapper.class);
            return tagMapper.getTagById(id)
                    .orElseThrow(() -> new MatchaException(String.format("Tag with id: %d doesn't exist", id)));
        }
    }

    @Override
    public Tag getTagByName(String name) {
        log.info("Get tag by name: {}", name);
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            TagMapper tagMapper = sqlSession.getMapper(TagMapper.class);
            return tagMapper.getTagByName(name)
                    .orElseThrow(() -> new MatchaException(String.format("Tag with name: %s doesn't exist", name)));
        }
    }

    @Override
    public List<Tag> getTagsByUserId(Long userId) {
        log.info("Get tags by user with id: {}", userId);
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            TagMapper tagMapper = sqlSession.getMapper(TagMapper.class);
            return tagMapper.getTagsByUserId(userId);
        }
    }

    @Override
    public void deleteTagsWithName(String name) {
        log.info("Delete tags with name: {}", name);
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            TagMapper tagMapper = sqlSession.getMapper(TagMapper.class);
            tagMapper.deleteTagsWithName(name);
            sqlSession.commit();
        } catch (Exception ex) {
            if (nonNull(sqlSession)) {
                sqlSession.rollback();
            }
            throw new MatchaException("Error to delete tags with name: " + name);
        } finally {
            if (nonNull(sqlSession)) {
                sqlSession.close();
            }
        }
    }

    @Override
    public void deleteTagById(Long id) {
        log.info("Delete tag by id: {}", id);
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            TagMapper tagMapper = sqlSession.getMapper(TagMapper.class);
            tagMapper.deleteTagById(id);
            sqlSession.commit();
        } catch (Exception ex) {
            if (nonNull(sqlSession)) {
                sqlSession.rollback();
            }
            throw new MatchaException("Error to delete tag with id: " + id);
        } finally {
            if (nonNull(sqlSession)) {
                sqlSession.close();
            }
        }
    }

    @Override
    public void deleteUserTags(Long userId) {
        log.info("Delete tags by user with id: {}", userId);
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            TagMapper tagMapper = sqlSession.getMapper(TagMapper.class);
            tagMapper.deleteUserTags(userId);
            sqlSession.commit();
        } catch (Exception ex) {
            if (nonNull(sqlSession)) {
                sqlSession.rollback();
            }
            throw new MatchaException("Error to delete tags by user with id: " + userId);
        } finally {
            if (nonNull(sqlSession)) {
                sqlSession.close();
            }
        }
    }

}
