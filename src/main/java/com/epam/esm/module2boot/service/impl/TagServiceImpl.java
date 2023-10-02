package com.epam.esm.module2boot.service.impl;

import com.epam.esm.module2boot.dao.TagDAO;
import com.epam.esm.module2boot.exception.dao.DataBaseConstrainException;
import com.epam.esm.module2boot.model.Tag;
import com.epam.esm.module2boot.service.TagService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService {

    private final TagDAO tagDAO;

    public TagServiceImpl(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }

    @Override
    public Tag getTagById(int id) {
        return tagDAO.getTagById(id);
    }

    public Tag createTag(String name) throws DataBaseConstrainException {
        return tagDAO.createTag(name);
    }

    public boolean deleteTag(int id) {
        return tagDAO.deleteTag(id);
    }

      @Override
    public Tag getMostUsedTagForUserID(int userID) {
        return tagDAO.getMostUsedTagForUserID(userID);
    }

    @Override
    public Page<Tag> getAllTags(Pageable pageable) {
        return tagDAO.findAll(pageable);
    }


    @Override
    public Integer getTagIdByName(String s) {
        Tag t = tagDAO.getTagByName(s);
        return t == null ? Tag.NO_ID : t.getId();
    }

}
