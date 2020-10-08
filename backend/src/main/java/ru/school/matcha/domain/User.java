package ru.school.matcha.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@JsonAutoDetect
public class User {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String gender;
    private Date birthday;
    private String description;
    private Boolean isActive;
    private Form form;
    private Date createTs;
    private Date updateTs;
    private Date deleteTs;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
