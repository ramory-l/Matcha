package ru.school.matcha.domain;

import lombok.Data;
import ru.school.matcha.enums.Gender;

import java.util.Date;

@Data
public class User {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private Date birthday;
    private String description;
    private Boolean isActive;
    private Form form;
    private Date createTs;
    private Date updateTs;
    private Date deleteTs;
}
