package ru.school.matcha.services.interfaces;

import ru.school.matcha.domain.Tag;

import java.util.List;

public interface TagService {

    void createTag(Tag tag);

    List<Tag> getTags();

    Tag getTagById(Long id);

    Tag getTagByName(String name);

    List<Tag> getTagsByUserId(Long userId);

    void deleteTagsWithName(String name);

    void deleteTagById(Long id);

    void deleteUserTags(Long userId);

}
