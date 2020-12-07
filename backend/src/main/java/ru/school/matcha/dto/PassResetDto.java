package ru.school.matcha.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

@Data
@JsonAutoDetect
public class PassResetDto {

    private String oldPass;
    private String newPass;
    private Long id;

}
