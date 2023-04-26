package com.epam.esm.module2boot.dao.jpaDataImpl;

import com.epam.esm.module2boot.dao.TagDAO;
import com.epam.esm.module2boot.dao.jpaDataImpl.jpaRepository.TagRepository;
import com.epam.esm.module2boot.exception.NotFoundException;
import com.epam.esm.module2boot.exception.dao.DataBaseConstrainException;
import com.epam.esm.module2boot.model.Tag;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Repository
@Profile("jpa-data")
public class TagDaoImpl implements TagDAO {

    private final TagRepository tagRepository;

    public TagDaoImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

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
    public Set<Tag> getTagsForCertID(int id) {
        return new HashSet<>(tagRepository.findTagsByGiftCertificateId(id));
    }

    @Override
    public Tag ensureTag(Tag tag) {
        Tag dbTag = tagRepository.findByName(tag.getName());
        return Objects.requireNonNullElseGet(dbTag, () -> tagRepository.save(tag));
    }

    @Override
    public Tag getTagByName(String name) throws NotFoundException {
        Tag tag = tagRepository.findByName(name);
        if (tag == null) throw new NotFoundException("No tag with name:" + name);
        return tag;
    }

    @Override
    public Tag getMostUsedTagForUserID(int userID) {
        return tagRepository.findMostUsedTagForUserID(userID);
    }


}
