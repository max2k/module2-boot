package com.epam.esm.module2boot.dao;

import com.epam.esm.module2boot.model.Tag;

public interface TagDAO {
    Tag createTag(String name);

    Tag getTagById(int id);
    void deleteTag(int id);
}
