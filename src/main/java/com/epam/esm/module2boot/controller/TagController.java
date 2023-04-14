package com.epam.esm.module2boot.controller;

import com.epam.esm.module2boot.exception.BadRequestException;
import com.epam.esm.module2boot.exception.NotFoundException;
import com.epam.esm.module2boot.model.Tag;
import com.epam.esm.module2boot.model.dto.TagDTO;
import com.epam.esm.module2boot.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Tag> getTagById(@PathVariable Integer id) {
        Tag tag = tagService.getTagById(id);
        if (tag != null) {
            return ResponseEntity.ok(tag);
        } else {
            throw new NotFoundException("Object with this id not found");
        }
    }

    @PostMapping("/new")
    public ResponseEntity<Tag> createTag(@RequestBody TagDTO tagDTO) {
        try {
            Tag createdTag = tagService.createTag(tagDTO.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTag);
        } catch (Exception e) {
            throw new BadRequestException("Tag with this name cannot be created");
        }


    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Integer id) {
        boolean deleted = tagService.deleteTag(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            throw new NotFoundException("Object with this id not found");
        }
    }


}
