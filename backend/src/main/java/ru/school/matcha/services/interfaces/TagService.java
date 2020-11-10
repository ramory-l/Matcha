package ru.school.matcha.services.interfaces;

import ru.school.matcha.domain.Tag;
import ru.school.matcha.domain.User;

import java.util.List;

public interface TagService {

    Long createTag(Tag tag);

    void createUserRefTag(String tagName, Long userId);

    List<Tag> getTags();

    Tag getTagById(Long id);

    Tag getTagByName(String name);

    List<Tag> getTagsByUserId(Long userId);

    void deleteTagById(Long id);

    void deleteUserTags(Long userId);

    void deleteUserRefTag(String tagName, Long userId);

}
