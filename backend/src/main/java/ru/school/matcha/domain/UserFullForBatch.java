package ru.school.matcha.domain;

import lombok.Data;
import ru.school.matcha.enums.Role;

import java.util.Date;

@Data
public class UserFullForBatch {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String gender;
    private Date birthday;
    private String description;
    private Form form;
    private Long rate;
    private Image avatar;
    private Role role;
    private Boolean isActive;
    private Double latitude;
    private Double longitude;
    private Boolean isVerified;
    private Boolean isOnline;
    private Date lastLoginDate;
    private Date createTs;
    private Date updateTs;
    private Date deleteTs;
    private Image image;

}
