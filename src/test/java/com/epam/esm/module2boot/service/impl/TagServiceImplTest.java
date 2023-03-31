package com.epam.esm.module2boot.service.impl;

import com.epam.esm.module2boot.model.Tag;
import com.epam.esm.module2boot.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TagServiceImplTest {

    @Autowired
    private TagService tagService;

    @Test
    void getTagById() {
        Tag tstTag=tagService.getTagById(1);
        assertNotNull(tstTag);
        assertEquals("tag1",tstTag.getName());
    }

    @Test
    void createTag() {
        String tagName="ServiceTagTest";
        Tag tstTag=tagService.createTag(tagName);

        assertEquals(tagName,tstTag.getName());
        assertFalse(tstTag.isNoId());
    }

    @Test
    void deleteTag() {
        assertTrue(tagService.deleteTag(1));
        assertFalse(tagService.deleteTag(10));
    }
}