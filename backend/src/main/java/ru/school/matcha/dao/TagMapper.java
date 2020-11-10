package ru.school.matcha.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import ru.school.matcha.domain.Tag;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TagMapper {

    void createTag(Tag tag);

    void createUserRefTag(@Param("tagId") Long tagId, @Param("userId") Long userId);

    void deleteUserRefTag(@Param("tagId") Long tagId, @Param("userId") Long userId);

    List<Tag> getTags();

    Optional<Tag> getTagById(Long id);

    Optional<Tag> getTagByName(String name);

    List<Tag> getTagsByUserId(Long userId);

    void deleteTagById(Long id);

    void deleteUserTags(Long userId);

}
