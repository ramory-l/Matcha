package ru.school.matcha.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.util.Date;

@Data
@JsonAutoDetect
public class UserFullDto {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String gender;
    private Date birthday;
    private String description;
    private FormDto form;
}
