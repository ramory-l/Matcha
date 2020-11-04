package ru.school.matcha.services.interfaces;

import java.util.List;
import java.util.Map;

public interface LikeService {

    void like(Long from, Long to, boolean like);

    List<Long> getLikes(Long id, Boolean like, Boolean outgoing);

    Map<String, List<Long>> getLikes(Long id, Boolean outgoing);

    void deleteLike(Long from, Long to, boolean like);

}
