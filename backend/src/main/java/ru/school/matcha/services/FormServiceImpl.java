package ru.school.matcha.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import ru.school.matcha.configs.MyBatisUtil;
import ru.school.matcha.dao.FormMapper;
import ru.school.matcha.domain.Form;
import ru.school.matcha.exceptions.MatchaException;
import ru.school.matcha.services.interfaces.FormService;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;

@Slf4j
public class FormServiceImpl implements FormService {

    @Override
    public List<Form> getAllForms() {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            FormMapper formMapper = sqlSession.getMapper(FormMapper.class);
            return formMapper.getAllForms();
        }
    }

    @Override
    public Optional<Form> getFormById(Long id) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            FormMapper formMapper = sqlSession.getMapper(FormMapper.class);
            return formMapper.getFormById(id);
        }
    }

    @Override
    public Optional<Form> getFormByUserId(Long id) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            FormMapper formMapper = sqlSession.getMapper(FormMapper.class);
            return formMapper.getFormByUserId(id);
        }
    }

    @Override
    public Long createForm(Form form) {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            FormMapper formMapper = sqlSession.getMapper(FormMapper.class);
            formMapper.createForm(form);
            sqlSession.commit();
            return form.getId();
        } catch (Exception ex) {
            if (nonNull(sqlSession)) {
                sqlSession.rollback();
            }
            throw new MatchaException("Error to create form");
        } finally {
            if (nonNull(sqlSession)) {
                sqlSession.close();
            }
        }
    }

    @Override
    public void updateForm(Form form, Long userId) {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            FormMapper formMapper = sqlSession.getMapper(FormMapper.class);
            formMapper.updateForm(form);
            sqlSession.commit();
        } catch (Exception ex) {
            if (nonNull(sqlSession)) {
                sqlSession.rollback();
            }
            throw new MatchaException("Error to update form by user id: " + userId);
        } finally {
            if (nonNull(sqlSession)) {
                sqlSession.close();
            }
        }
    }

    @Override
    public void deleteFormById(Long id) {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            FormMapper formMapper = sqlSession.getMapper(FormMapper.class);
            formMapper.deleteFormById(id);
            sqlSession.commit();
        } catch (Exception ex) {
            if (nonNull(sqlSession)) {
                sqlSession.rollback();
            }
            throw new MatchaException("Error to delete form by id: " + id);
        } finally {
            if (nonNull(sqlSession)) {
                sqlSession.close();
            }
        }
    }

    @Override
    public void deleteFormByUserId(Long id) {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            FormMapper formMapper = sqlSession.getMapper(FormMapper.class);
            formMapper.deleteFormByUserId(id);
            sqlSession.commit();
        } catch (Exception ex) {
            if (nonNull(sqlSession)) {
                sqlSession.rollback();
            }
            throw new MatchaException("Error to delete form by user id: " + id);
        } finally {
            if (nonNull(sqlSession)) {
                sqlSession.close();
            }
        }
    }

    @Override
    public void deleteAllInactiveForms() {
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            FormMapper formMapper = sqlSession.getMapper(FormMapper.class);
            formMapper.deleteAllInactiveForms();
            sqlSession.commit();
        } catch (Exception ex) {
            if (nonNull(sqlSession)) {
                sqlSession.rollback();
            }
            throw new MatchaException("Error to delete all inactive forms");
        } finally {
            if (nonNull(sqlSession)) {
                sqlSession.close();
            }
        }
    }

}
