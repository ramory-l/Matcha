package ru.school.matcha.domain;

import lombok.Data;

@Data
public class Like {
    private Image avatar;
    private String username;
    private Long id;
    private Long from;
    private Long to;
}
