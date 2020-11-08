package ru.school.matcha.dao;

import org.apache.ibatis.annotations.Mapper;
import ru.school.matcha.domain.Tag;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TagMapper {

    void createTag(Tag tag);

    List<Tag> getTags();

    Optional<Tag> getTagById(Long id);

    Optional<Tag> getTagByName(String name);

    List<Tag> getTagsByUserId(Long userId);

    void deleteTagsWithName(String name);

    void deleteTagById(Long id);

    void deleteUserTags(Long userId);

}
