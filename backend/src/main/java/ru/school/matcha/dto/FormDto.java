package ru.school.matcha.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

@Data
@JsonAutoDetect
public class FormDto {

    private Long id;
    private boolean man;
    private boolean woman;
    private boolean friendship;
    private boolean love;
    private boolean sex;
    private boolean flirt;
    private Integer ageFrom;
    private Integer ageTo;

}
