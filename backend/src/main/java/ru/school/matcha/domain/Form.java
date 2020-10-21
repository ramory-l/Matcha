package ru.school.matcha.domain;

import lombok.Data;

@Data
public class Form {
    private Long id;
    private boolean man;
    private boolean woman;
    private boolean friendship;
    private boolean love;
    private boolean sex;
    private boolean flirt;
}
