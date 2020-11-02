package ru.school.matcha.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LikeMapper {

    void like(@Param("from") Long from, @Param("to") Long to, @Param("like") Boolean like);

    List<Long> getLikes(@Param("id") Long id, @Param("like") Boolean like, @Param("outgoing") Boolean outgoing);

    void deleteLike(@Param("from") Long from, @Param("to") Long to, @Param("like") Boolean like);

    void addRate(Long id);

    void deleteRate(Long id);

    Long getLike(@Param("from") Long from, @Param("to") Long to, @Param("like") Boolean like);

}
