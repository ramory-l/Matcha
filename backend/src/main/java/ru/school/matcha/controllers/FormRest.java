package ru.school.matcha.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.school.matcha.domain.Form;
import ru.school.matcha.dto.FormDto;
import ru.school.matcha.exceptions.MatchaException;
import ru.school.matcha.services.interfaces.CollectionConversionService;
import ru.school.matcha.services.interfaces.FormService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("form")
public class FormRest {

    private final FormService formService;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final CollectionConversionService conversionService;

    @Autowired
    public FormRest(FormService formService, CollectionConversionService conversionService) {
        this.formService = formService;
        this.conversionService = conversionService;
    }

    @GetMapping("all")
    public ResponseEntity<Object> getAllForms() {
        try {
            List<Form> forms = formService.getAllForms();
            List<FormDto> result = conversionService.convertCollection(forms, FormDto.class, Collectors.toList());
            return ResponseEntity.ok(result);
        } catch (MatchaException ex) {
            logger.error("Failed to get all forms", ex);
            return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body(ex.getMessage());
        } catch (Exception ex) {
            logger.error("An unexpected error occurred while trying to get all forms", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getFormById(@PathVariable("id") Long id) {
        try {
            Form form = formService.getFormById(id).orElseThrow(() -> new MatchaException("Failed to get form by id: " + id));
            FormDto result = conversionService.convert(form, FormDto.class);
            return ResponseEntity.ok(result);
        } catch (MatchaException ex) {
            logger.error("Failed to get form by id: {}", id, ex);
            return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body(ex.getMessage());
        } catch (Exception ex) {
            logger.error("An unexpected error occurred while trying to get form by id: {}", id, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Object> getFormByUserId(@PathVariable("id") Long id) {
        try {
            Form form = formService.getFormByUserId(id).orElseThrow(() -> new MatchaException("Failed to get form by user id: " + id));
            FormDto result = conversionService.convert(form, FormDto.class);
            return ResponseEntity.ok(result);
        } catch (MatchaException ex) {
            logger.error("Failed to get form by user id: {}", id, ex);
            return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body(ex.getMessage());
        } catch (Exception ex) {
            logger.error("An unexpected error occurred while trying to get form by user id: {}", id, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Object> createForm(@RequestBody FormDto formDto) {
        try {
            Form form = conversionService.convert(formDto, Form.class);
            formService.createForm(form);
            return ResponseEntity.ok().build();
        } catch (MatchaException ex) {
            logger.error("Failed to create form", ex);
            return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body(ex.getMessage());
        } catch (Exception ex) {
            logger.error("An unexpected error occurred while trying to create form", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping
    public ResponseEntity<Object> updateForm(@RequestBody FormDto formDto) {
        try {
            Form form = conversionService.convert(formDto, Form.class);
            formService.updateForm(form);
            return ResponseEntity.ok().build();
        } catch (MatchaException ex) {
            logger.error("Failed to update form", ex);
            return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body(ex.getMessage());
        } catch (Exception ex) {
            logger.error("An unexpected error occurred while trying to update form", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFormById(@PathVariable("id") Long id) {
        try {
            formService.deleteFormById(id);
            return ResponseEntity.ok().build();
        } catch (MatchaException ex) {
            logger.error("Failed to delete form by id: {}", id, ex);
            return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body(ex.getMessage());
        } catch (Exception ex) {
            logger.error("An unexpected error occurred while trying to delete form by id: {}", id, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Object> deleteFormByUserId(@PathVariable("id") Long userId) {
        try {
            formService.deleteFormByUserId(userId);
            return ResponseEntity.ok().build();
        } catch (MatchaException ex) {
            logger.error("Failed to delete form by user id: {}", userId, ex);
            return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body(ex.getMessage());
        } catch (Exception ex) {
            logger.error("An unexpected error occurred while trying to delete form by user id: {}", userId, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
