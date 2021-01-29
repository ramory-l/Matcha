package ru.school.matcha.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
@JsonAutoDetect
@AllArgsConstructor
public class PageDto<T> {
    @NonNull
    private List<T> data;
    @NonNull
    private long total;
    @NonNull
    private int offset;
}
