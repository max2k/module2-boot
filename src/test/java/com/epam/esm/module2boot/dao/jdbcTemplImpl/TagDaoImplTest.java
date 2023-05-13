package com.epam.esm.module2boot.dao.jdbcTemplImpl;

import com.epam.esm.module2boot.dao.TagDAO;
import com.epam.esm.module2boot.exception.NotFoundException;
import com.epam.esm.module2boot.exception.dao.DataBaseConstrainException;
import com.epam.esm.module2boot.model.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class TagDaoImplTest {

    @Autowired
    private TagDAO tagDao;


    @Test
    void createTag() throws DataBaseConstrainException {
        String testName = "test tag1";

        Tag createdTag = tagDao.createTag(testName);
        assertNotNull(createdTag);

        Tag tagFromDataBase = tagDao.getTagById(createdTag.getId());

        assertNotNull(tagFromDataBase);
        assertEquals(testName, tagFromDataBase.getName());

        // if duplicated name found
        assertThrows(DataBaseConstrainException.class, () -> tagDao.createTag("tag1"));

    }


    @Test
    void getTagById() {
        Tag tstTag = tagDao.getTagById(1);
        assertNotNull(tstTag);
        assertEquals("tag2", tstTag.getName());
    }

    @Test
    void deleteTag() {

        assertTrue(tagDao.deleteTag(1));
        assertThrows(NotFoundException.class, () -> tagDao.getTagById(1));

        assertThrows(NotFoundException.class, () -> tagDao.deleteTag(10000));

    }
}