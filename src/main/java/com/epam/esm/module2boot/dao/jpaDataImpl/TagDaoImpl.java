package com.epam.esm.module2boot.dao.jpaDataImpl;

import com.epam.esm.module2boot.dao.TagDAO;
import com.epam.esm.module2boot.dao.jpaDataImpl.jpaRepository.TagRepository;
import com.epam.esm.module2boot.exception.NotFoundException;
import com.epam.esm.module2boot.exception.dao.DataBaseConstrainException;
import com.epam.esm.module2boot.model.Tag;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
@Profile("jpa-data")
public class TagDaoImpl implements TagDAO {

    private final TagRepository tagRepository;

    @Override
    public Tag createTag(String name) throws DataBaseConstrainException {
        Tag tag = new Tag();
        tag.setName(name);
        try {
            return tagRepository.save(tag);
        } catch (DataIntegrityViolationException inner) {
            throw new DataBaseConstrainException("There already exist tag with name:" + name, inner);
        }

    }

    @Override
    public Tag getTagById(int id) throws NotFoundException {
        return tagRepository.findById((long) id)
                .orElseThrow(() -> new NotFoundException("No tag with id:" + id));
    }

    @Override
    public boolean deleteTag(int id) throws NotFoundException {
        Long longTagId = (long) id;

        tagRepository.findById(longTagId)
                .orElseThrow(() -> new NotFoundException("No tag found with id:" + id));

        tagRepository.deleteById(longTagId);
        return true;
    }

    @Override
    public Tag getMostUsedTagForUserID(int userID) {
        return tagRepository.findMostUsedTagForUserID(userID);
    }

    @Override
    public Page<Tag> findAll(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

}
