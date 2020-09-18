package ru.school.matcha.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.school.matcha.dao.FormMapper;
import ru.school.matcha.domain.Form;
import ru.school.matcha.services.interfaces.FormService;

import java.util.List;
import java.util.Optional;

@Service
public class FormServiceImpl implements FormService {

    private final FormMapper formMapper;

    @Autowired
    public FormServiceImpl(FormMapper formMapper) {
        this.formMapper = formMapper;
    }

    @Override
    public List<Form> getAllForms() {
        return formMapper.getAllForms();
    }

    @Override
    public Optional<Form> getFormById(Long id) {
        return formMapper.getFormById(id);
    }

    @Override
    public Optional<Form> getFormByUserId(Long id) {
        return formMapper.getFormByUserId(id);
    }

    @Override
    public void createForm(Form form) {
        formMapper.createForm(form);
    }

    @Override
    public void updateForm(Form form) {
        formMapper.updateForm(form);
    }

    @Override
    public void deleteFormById(Long id) {
        formMapper.deleteFormById(id);
    }

    @Override
    public void deleteFormByUserId(Long id) {
        formMapper.deleteFormByUserId(id);
    }
}
