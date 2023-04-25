package com.epam.esm.module2boot.service;

import com.epam.esm.module2boot.exception.dao.DataBaseConstrainException;
import com.epam.esm.module2boot.model.Tag;

public interface TagService {
    Tag getTagById(int id);

    Tag createTag(String name) throws DataBaseConstrainException;

    boolean deleteTag(int id);

    Tag ensureTag(Tag tag) throws DataBaseConstrainException;

    Tag getTagByName(String name);

    Tag getMostUsedTagForUserID(int userID);
}
