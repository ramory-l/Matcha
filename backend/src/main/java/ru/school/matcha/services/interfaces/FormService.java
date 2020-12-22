package ru.school.matcha.services.interfaces;

import ru.school.matcha.domain.Form;

import java.util.List;

public interface FormService {

    List<Form> getAllForms();

    Form getFormById(Long id);

    Form createForm(Form form);

    void updateForm(Form form);

    void deleteFormById(Long id);

    void deleteAllInactiveForms();

}
