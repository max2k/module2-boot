package com.epam.esm.module2boot.service.impl;

import com.epam.esm.module2boot.exception.NotFoundException;
import com.epam.esm.module2boot.exception.dao.DataBaseConstrainException;
import com.epam.esm.module2boot.model.Tag;
import com.epam.esm.module2boot.service.TagService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class TagServiceImplTest {

    @Autowired
    private TagService tagService;


    @Test
    void getTagById() {
        Tag tstTag = tagService.getTagById(1);
        assertNotNull(tstTag);
        assertEquals("tag2", tstTag.getName());
    }

    @Test
    void createTag() throws DataBaseConstrainException {
        String tagName = "ServiceTagTest";
        Tag tstTag = tagService.createTag(tagName);

        assertEquals(tagName, tstTag.getName());
        assertFalse(tstTag.isNoId());

        assertThrows(DataBaseConstrainException.class, () -> tagService.createTag(tagName));
    }

    @Test
    void deleteTag() {
        assertTrue(tagService.deleteTag(1));
        assertThrows(NotFoundException.class, () -> tagService.deleteTag(10000));
    }


}