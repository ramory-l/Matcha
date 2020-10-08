package ru.school.matcha.services;

import ru.school.matcha.dao.FormMapper;
import ru.school.matcha.domain.Form;
import ru.school.matcha.services.interfaces.FormService;

import java.util.List;
import java.util.Optional;

public class FormServiceImpl implements FormService {

//    private final FormMapper formMapper;

    public FormServiceImpl() {
//        this.formMapper = formMapper;
    }

    @Override
    public List<Form> getAllForms() {
//        return formMapper.getAllForms();
        return null;
    }

    @Override
    public Optional<Form> getFormById(Long id) {
//        return formMapper.getFormById(id);
        return null;
    }

    @Override
    public Optional<Form> getFormByUserId(Long id) {
//        return formMapper.getFormByUserId(id);
        return null;
    }

    @Override
    public void createForm(Form form) {
//        formMapper.createForm(form);
    }

    @Override
    public void updateForm(Form form, Long userId) {
//        formMapper.updateForm(form);
    }

    @Override
    public void deleteFormById(Long id) {
//        formMapper.deleteFormById(id);
    }

    @Override
    public void deleteFormByUserId(Long id) {
//        formMapper.deleteFormByUserId(id);
    }
}
