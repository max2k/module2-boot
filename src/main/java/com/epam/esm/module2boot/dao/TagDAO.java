package com.epam.esm.module2boot.dao;

import com.epam.esm.module2boot.exception.NotFoundException;
import com.epam.esm.module2boot.exception.dao.DataBaseConstrainException;
import com.epam.esm.module2boot.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagDAO {
    Tag createTag(String name) throws DataBaseConstrainException;

    Tag getTagById(int id) throws NotFoundException;

    boolean deleteTag(int id) throws NotFoundException;


    Tag ensureTag(Tag tag);


    Tag getMostUsedTagForUserID(int userID);

    Page<Tag> findAll(Pageable pageable);
}
