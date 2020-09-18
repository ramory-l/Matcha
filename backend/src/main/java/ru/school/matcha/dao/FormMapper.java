package ru.school.matcha.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import ru.school.matcha.domain.Form;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface FormMapper {

    List<Form> getAllForms();

    Optional<Form> getFormById(Long id);

    Optional<Form> getFormByUserId(Long id);

    void createForm(Form form);

    void updateForm(Form form);

    void deleteFormById(Long id);

    void deleteFormByUserId(Long id);
}
