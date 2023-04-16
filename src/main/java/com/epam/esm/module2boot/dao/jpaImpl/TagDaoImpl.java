package com.epam.esm.module2boot.dao.jpaImpl;

import com.epam.esm.module2boot.dao.TagDAO;
import com.epam.esm.module2boot.exception.NotFoundException;
import com.epam.esm.module2boot.exception.dao.DataBaseConstrainException;
import com.epam.esm.module2boot.model.Tag;
import jakarta.persistence.*;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
@Profile("jpa")
public class TagDaoImpl implements TagDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Tag createTag(String name) throws DataBaseConstrainException {
        try {
            Tag tag = new Tag();
            tag.setName(name);
            entityManager.persist(tag);
            entityManager.flush();
            return tag;
        } catch (PersistenceException inner) {
            throw new DataBaseConstrainException("Creating tag with name " + name + " failed.", inner);
        }

    }

    @Override
    public Tag getTagById(int id) throws NotFoundException {
        Tag tag = entityManager.find(Tag.class, id);
        if ( tag == null ) throw new NotFoundException("Tag not found with id:"+id);
        return tag;
    }

    @Override
    public boolean deleteTag(int id) throws NotFoundException {

        Tag tag = entityManager.find(Tag.class, id);
        if (tag != null) {
            entityManager.remove(tag);
            entityManager.flush();
        }
        else{
            throw new NotFoundException("Tag not found with id:"+id);
        }
        return true;
    }

    @Override
    public Set<Tag> getTagsForCertID(int id) {
        TypedQuery<Tag> query = entityManager.createQuery(
                "SELECT tag FROM Tag tag JOIN tag.certificates certificate WHERE certificate.id = :id", Tag.class);
        query.setParameter("id", id);
        List<Tag> tagList = query.getResultList();
        return new HashSet<>(tagList);
    }

    @Override
    public Tag ensureTag(Tag tag) throws DataBaseConstrainException {
        try {
            return getTagByName(tag.getName());
        } catch (NotFoundException e) {
            return createTag(tag.getName());
        }
    }

    @Override
    public Tag getTagByName(String name) throws NotFoundException {
        TypedQuery<Tag> query = entityManager.createQuery(
                "SELECT t FROM Tag t WHERE t.name = :name", Tag.class);
        query.setParameter("name", name);

        try {
            return query.getSingleResult();
        } catch (NoResultException noResultException){
            throw new NotFoundException("No tag found with name:"+name);
        }

    }
}
