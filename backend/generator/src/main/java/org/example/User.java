package org.example;

import lombok.Data;
import org.example.Form;

import java.util.Date;

@Data
public class User {
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String gender;
    private Date birthday;
    private String description;
    private Form form;
}
