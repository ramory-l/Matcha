package ru.school.matcha.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

@Data
@JsonAutoDetect
public class CredentialsDto {
    private String username;
    private String password;
    private String email;
}
