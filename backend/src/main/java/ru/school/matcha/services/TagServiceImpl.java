package ru.school.matcha.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import ru.school.matcha.dao.TagMapper;
import ru.school.matcha.domain.Tag;
import ru.school.matcha.exceptions.MatchaException;
import ru.school.matcha.exceptions.NotFoundException;
import ru.school.matcha.services.interfaces.TagService;
import ru.school.matcha.services.interfaces.UserService;
import ru.school.matcha.utils.MyBatisUtil;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;

@Slf4j
public class TagServiceImpl implements TagService {

    private static final UserService userService = new UserServiceImpl();

    @Override
    public void createTag(Tag tag) {
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
    public void createUserRefTag(String tagName, Long userId) {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            TagMapper tagMapper = sqlSession.getMapper(TagMapper.class);
            Optional<Tag> tagOptional = tagMapper.getTagByName(tagName);
            Tag tag;
            if (!tagOptional.isPresent()) {
                tag = new Tag();
                tag.setTag(tagName);
                createTag(tag);
            } else {
                tag = tagOptional.get();
            }
            tagMapper.createUserRefTag(tag.getId(), userId);
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
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            TagMapper tagMapper = sqlSession.getMapper(TagMapper.class);
            return tagMapper.getTags();
        }
    }

    @Override
    public List<Tag> getTagsByUserId(Long userId) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            TagMapper tagMapper = sqlSession.getMapper(TagMapper.class);
            return tagMapper.getTagsByUserId(userId);
        }
    }

    @Override
    public void deleteTagById(Long id) {
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
    public void deleteUserRefTag(String tagName, Long userId) {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            TagMapper tagMapper = sqlSession.getMapper(TagMapper.class);
            Tag tag = tagMapper.getTagByName(tagName)
                    .orElseThrow(() -> new MatchaException("Tag with name " + tagName + " doesn't exist"));
            tagMapper.deleteUserRefTag(tag.getId(), userId);
            sqlSession.commit();
            if (userService.getUsersByTagId(tag.getId(), 0L).isEmpty()) {
                deleteTagById(tag.getId());
            }
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

    @Override
    public Tag getTagByName(String name) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            TagMapper tagMapper = sqlSession.getMapper(TagMapper.class);
            return tagMapper.getTagByName(name).orElseThrow(NotFoundException::new);
        }
    }

}
