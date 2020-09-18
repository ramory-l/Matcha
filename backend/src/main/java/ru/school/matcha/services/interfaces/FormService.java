package ru.school.matcha.services.interfaces;

import ru.school.matcha.domain.Form;

import java.util.List;
import java.util.Optional;

public interface FormService {

    List<Form> getAllForms();

    Optional<Form> getFormById(Long id);

    Optional<Form> getFormByUserId(Long id);

    void createForm(Form form);

    void updateForm(Form form);

    void deleteFormById(Long id);

    void deleteFormByUserId(Long id);
}