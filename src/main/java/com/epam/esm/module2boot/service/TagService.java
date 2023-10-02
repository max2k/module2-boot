package com.epam.esm.module2boot.service;

import com.epam.esm.module2boot.exception.dao.DataBaseConstrainException;
import com.epam.esm.module2boot.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagService {
    Tag getTagById(int id);

    Tag createTag(String name) throws DataBaseConstrainException;

    boolean deleteTag(int id);

    Tag getMostUsedTagForUserID(int userID);

    Page<Tag> getAllTags(Pageable pageable);

    Integer getTagIdByName(String s);
}
