package ru.school.matcha.services.interfaces;

import java.util.List;

public interface LikeService {

    void like(Long from, Long to, boolean like);

    List<Long> getLikes(Long id, Boolean like, Boolean outgoing);

    void deleteLike(Long from, Long to, boolean like);

}
