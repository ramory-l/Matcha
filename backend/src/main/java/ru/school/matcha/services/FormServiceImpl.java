package ru.school.matcha.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import ru.school.matcha.configs.MyBatisUtil;
import ru.school.matcha.dao.FormMapper;
import ru.school.matcha.domain.Form;
import ru.school.matcha.services.interfaces.FormService;

import java.util.List;
import java.util.Optional;

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
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            FormMapper formMapper = sqlSession.getMapper(FormMapper.class);
            formMapper.createForm(form);
            sqlSession.commit();
            return form.getId();
        }
    }

    @Override
    public void updateForm(Form form, Long userId) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            FormMapper formMapper = sqlSession.getMapper(FormMapper.class);
            formMapper.updateForm(form);
            sqlSession.commit();
        }
    }

    @Override
    public void deleteFormById(Long id) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            FormMapper formMapper = sqlSession.getMapper(FormMapper.class);
            formMapper.deleteFormById(id);
            sqlSession.commit();
        }
    }

    @Override
    public void deleteFormByUserId(Long id) {
        try (SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession()) {
            FormMapper formMapper = sqlSession.getMapper(FormMapper.class);
            formMapper.deleteFormByUserId(id);
            sqlSession.commit();
        }
    }
}
