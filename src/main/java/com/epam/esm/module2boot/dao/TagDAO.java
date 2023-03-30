package com.epam.esm.module2boot.dao;

import com.epam.esm.module2boot.model.Tag;

import java.util.Set;

public interface TagDAO {
    Tag createTag(String name);

    Tag getTagById(int id);
    void deleteTag(int id);

    Set<Tag> getTagsForCertID(int id);
}
