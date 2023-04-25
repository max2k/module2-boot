package com.epam.esm.module2boot.dao;

import com.epam.esm.module2boot.exception.NotFoundException;
import com.epam.esm.module2boot.exception.dao.DataBaseConstrainException;
import com.epam.esm.module2boot.model.Tag;

import java.util.Set;

public interface TagDAO {
    Tag createTag(String name) throws DataBaseConstrainException;

    Tag getTagById(int id) throws NotFoundException;

    boolean deleteTag(int id) throws NotFoundException;

    Set<Tag> getTagsForCertID(int id);

    Tag ensureTag(Tag tag) throws DataBaseConstrainException;

    Tag getTagByName(String name) throws NotFoundException;

    Tag getMostUsedTagForUserID(int userID);
}
