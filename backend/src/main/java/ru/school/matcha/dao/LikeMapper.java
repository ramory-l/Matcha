package ru.school.matcha.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import ru.school.matcha.domain.Like;

import java.util.List;

@Mapper
public interface LikeMapper {

    void like(@Param("from") Long from, @Param("to") Long to, @Param("isLike") Boolean isLike);

    List<Like> getLikes(@Param("userId") Long userId, @Param("isLike") Boolean isLike, @Param("outgoing") Boolean outgoing);

    void deleteLike(@Param("from") Long from, @Param("to") Long to, @Param("isLike") Boolean isLike);

    void addRate(Long userId);

    void deleteRate(Long userId);

    Long getLike(@Param("from") Long from, @Param("to") Long to, @Param("isLike") Boolean isLike);

}
