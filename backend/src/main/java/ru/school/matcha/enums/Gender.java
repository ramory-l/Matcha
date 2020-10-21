package ru.school.matcha.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public enum Gender {
    MAN("man"),
    WOMAN("woman");

    @Getter
    @Setter
    private String gender;
}
