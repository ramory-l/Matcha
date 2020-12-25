package ru.school.matcha.services.interfaces;

import ru.school.matcha.domain.Like;
import ru.school.matcha.domain.Matcha;

import java.util.List;
import java.util.Map;

public interface LikeService {

    void like(Long from, Long to, boolean isLike);

    List<Like> getLikesByUserId(Long userId, Boolean isLike, Boolean outgoing);

    Map<String, List<Like>> getLikesByUserId(Long userId, Boolean outgoing);

    void deleteLike(Long from, Long to, boolean isLike);

    public List<Matcha> getMatches(long id);

}
