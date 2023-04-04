package com.epam.esm.module2boot.service.impl;

import com.epam.esm.module2boot.dao.TagDAO;
import com.epam.esm.module2boot.model.Tag;
import com.epam.esm.module2boot.service.TagService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService {

    private final TagDAO tagDAO;

    public TagServiceImpl(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }

    @Override
    public Tag getTagById(int id) {
        try {
            return tagDAO.getTagById(id);
        }catch (EmptyResultDataAccessException e){
            return null;
        }

    }

    public Tag createTag(String name) {
         return tagDAO.createTag(name);
    }
    public boolean deleteTag(int id) {
         return  tagDAO.deleteTag(id);
    }

    @Override
    public Tag ensureTag(Tag tag) {
        return tagDAO.ensureTag(tag);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagDAO.getTagByName(name);
    }

}
