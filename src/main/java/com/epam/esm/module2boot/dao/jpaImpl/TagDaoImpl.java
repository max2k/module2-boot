package com.epam.esm.module2boot.dao.jpaImpl;

import com.epam.esm.module2boot.dao.TagDAO;
import com.epam.esm.module2boot.model.Tag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository
@Transactional
@Profile("jpa")
public class TagDaoImpl implements TagDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Tag createTag(String name) {
        Tag tag = new Tag();
        tag.setName(name);
        entityManager.persist(tag);
        entityManager.flush();
        return tag;
    }

    @Override
    public Tag getTagById(int id) {
        return null;
    }

    @Override
    public boolean deleteTag(int id) {
        return false;
    }

    @Override
    public Set<Tag> getTagsForCertID(int id) {
        return null;
    }

    @Override
    public Tag ensureTag(Tag tag) {
        return null;
    }

    @Override
    public Tag getTagByName(String name) {
        return null;
    }
}
