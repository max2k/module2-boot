package com.epam.esm.module2boot.dao.jdbcTemplImpl;

import com.epam.esm.module2boot.dao.TagDAO;
import com.epam.esm.module2boot.model.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
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

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.H2;

@JdbcTest
@ExtendWith(SpringExtension.class)
class TagDaoImplTest {

    private TagDAO tagDao;
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        EmbeddedDatabase db = new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(H2)
                .setScriptEncoding("UTF-8")
                .addDefaultScripts()
                .build();
        jdbcTemplate = new JdbcTemplate(db);
        tagDao = new TagDaoImpl(jdbcTemplate);
    }

    @Test
    void createTag() {
        String testName="test tag1";

        tagDao.createTag(testName);

        Tag tstTag=jdbcTemplate.queryForObject("select * from tag where name=?",new TagRowMapper(),testName);
        assertNotNull(tstTag);
        assertEquals(testName,tstTag.getName());


    }

    @Test
    void getTagById() {
        Tag tstTag=tagDao.getTagById(1);
        assertNotNull(tstTag);
        assertEquals("tag1",tstTag.getName());
    }

    @Test
    void deleteTag() {
        tagDao.deleteTag(1);
        assertThrows(EmptyResultDataAccessException.class, () ->
                jdbcTemplate.queryForObject("select * from tag where id=?",new TagRowMapper(),1)
        );

    }
}