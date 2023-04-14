package com.epam.esm.module2boot.service;

import com.epam.esm.module2boot.model.Tag;

public interface TagService {
    Tag getTagById(int id);

    Tag createTag(String name);

    boolean deleteTag(int id);

    Tag ensureTag(Tag tag);

    Tag getTagByName(String name);
}
