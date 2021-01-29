package ru.school.matcha.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonAutoDetect
public class FormDto {
    private Long id;
    private boolean man;
    private boolean woman;
    private Integer ageFrom;
    private Integer ageTo;
    private Integer rateFrom;
    private Integer rateTo;
    private Integer radius;
}
