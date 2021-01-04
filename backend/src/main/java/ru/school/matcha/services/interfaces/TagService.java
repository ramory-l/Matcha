package ru.school.matcha.services.interfaces;

import ru.school.matcha.domain.Tag;

import java.util.List;

public interface TagService {

    void createTag(Tag tag);

    void createUserRefTag(String tagName, Long userId);

    List<Tag> getTags();

    List<Tag> getTagsByUserId(Long userId);

    Tag getTagByName(String name);

    void deleteTagById(Long id);

    void deleteUserRefTag(String tagName, Long userId);

    List<Tag> getTopTags();

    List<Tag> getMutualTags(Long firstUserId, Long secondUserId);

}
