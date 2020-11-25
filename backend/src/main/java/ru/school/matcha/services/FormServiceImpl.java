package ru.school.matcha.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import ru.school.matcha.exceptions.NotFoundException;
import ru.school.matcha.utils.MyBatisUtil;
import ru.school.matcha.dao.FormMapper;
import ru.school.matcha.domain.Form;
import ru.school.matcha.exceptions.MatchaException;
import ru.school.matcha.services.interfaces.FormService;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Slf4j
public class FormServiceImpl implements FormService {

    @Override
    public List<Form> getAllForms() {
        log.debug("Get all forms");
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            FormMapper formMapper = sqlSession.getMapper(FormMapper.class);
            return formMapper.getAllForms();
        }
    }

    @Override
    public Form getFormById(Long id) {
        log.debug("Get form by id: {}", id);
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            FormMapper formMapper = sqlSession.getMapper(FormMapper.class);
            return formMapper.getFormById(id).orElseThrow(NotFoundException::new);
        }
    }

    @Override
    public Form createForm(Form form) {
        log.debug("Create new form");
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            FormMapper formMapper = sqlSession.getMapper(FormMapper.class);
            formMapper.createForm(form);
            sqlSession.commit();
            return form;
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
    public void updateForm(Form form) {
        if (isNull(form.getId())) {
            throw new MatchaException("The update could not be completed because the identifier was not specified");
        }
        log.debug("Update form by id: {}", form.getId());
        SqlSession sqlSession = null;
        try {
            sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            FormMapper formMapper = sqlSession.getMapper(FormMapper.class);
            formMapper.updateFormById(form);
            sqlSession.commit();
        } catch (Exception ex) {
            if (nonNull(sqlSession)) {
                sqlSession.rollback();
            }
            throw new MatchaException("Error to update form by id: " + form.getId());
        } finally {
            if (nonNull(sqlSession)) {
                sqlSession.close();
            }
        }
    }

    @Override
    public void deleteFormById(Long id) {
        log.debug("Delete form by id: {}", id);
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
    public void deleteAllInactiveForms() {
        log.debug("Delete all inactive forms");
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
