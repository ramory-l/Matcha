package ru.school.matcha.services.interfaces;

import ru.school.matcha.domain.Form;

import java.util.List;
import java.util.Optional;

public interface FormService {

    List<Form> getAllForms();

    Form getFormById(Long id);

    Long createForm(Form form);

    void updateForm(Form form);

    void deleteFormById(Long id);

    void deleteAllInactiveForms();

}
