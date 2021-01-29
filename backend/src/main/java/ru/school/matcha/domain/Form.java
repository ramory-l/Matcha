package ru.school.matcha.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Form {
    private Long id;
    private boolean man;
    private boolean woman;
    private Integer ageFrom;
    private Integer ageTo;
    private Integer rateFrom;
    private Integer rateTo;
    private Integer radius;
}
