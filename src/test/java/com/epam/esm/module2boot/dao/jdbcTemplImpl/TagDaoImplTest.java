package com.epam.esm.module2boot.dao.jdbcTemplImpl;

import com.epam.esm.module2boot.dao.TagDAO;
import com.epam.esm.module2boot.model.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class TagDaoImplTest {

    @Autowired
    private TagDAO tagDao;


    @Test
    void createTag() {
        String testName="test tag1";

        Tag createdTag=tagDao.createTag(testName);
        assertNotNull(createdTag);

        Tag tagFromDataBase=tagDao.getTagById(createdTag.getId());

        assertNotNull(tagFromDataBase);
        assertEquals(testName,tagFromDataBase.getName());


    }

    @Test
    void getTagById() {
        Tag tstTag=tagDao.getTagById(1);
        assertNotNull(tstTag);
        assertEquals("tag1",tstTag.getName());
    }

    @Test
    void deleteTag() {
        Tag tstTag=tagDao.createTag("delete test");

        assertTrue( tagDao.deleteTag(tstTag.getId()) );

        assertThrows(EmptyResultDataAccessException.class,() ->  tagDao.getTagById(tstTag.getId()));


    }
}