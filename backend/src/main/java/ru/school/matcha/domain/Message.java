package ru.school.matcha.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Message {
    private Long id;
    private Long from;
    private Long to;
    private String message;
    private Date createTs;
    private String type;
    private String username;
    private Image avatar;
}
