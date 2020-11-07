package ru.school.matcha.services.interfaces;

import java.util.List;
import java.util.Map;

public interface LikeService {

    void like(Long from, Long to, boolean isLike);

    List<Long> getLikesByUserId(Long userId, Boolean isLike, Boolean outgoing);

    Map<String, List<Long>> getLikesByUserId(Long userId, Boolean outgoing);

    void deleteLike(Long from, Long to, boolean isLike);

}
