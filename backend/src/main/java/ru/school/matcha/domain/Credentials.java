package ru.school.matcha.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

@Data
@JsonAutoDetect
public class Credentials {
    private String username;
    private String password;
    private String email;
}
