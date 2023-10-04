package com.epam.esm.module2boot.controller;

import com.epam.esm.module2boot.dto.TagDTO;
import com.epam.esm.module2boot.exception.BadRequestException;
import com.epam.esm.module2boot.model.Tag;
import com.epam.esm.module2boot.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

        tag.add(linkTo(methodOn(TagController.class).getTagById(id)).withSelfRel());
        return ResponseEntity.ok(tag);

    }

    @GetMapping("/all")
    public ResponseEntity<PagedModel<Tag>> getAllTags(@RequestParam(required = false, defaultValue = "0") int page,
                                                      @RequestParam(required = false, defaultValue = "10") int size) {

        Page<Tag> tagPage = tagService.getAllTags(PageRequest.of(page, size));

        tagPage.forEach(tag -> tag.add(linkTo(methodOn(TagController.class).getTagById(tag.getId())).withSelfRel()));

        PagedModel<Tag> pagedModel = PagedModel.of(tagPage.toList(),
                new PagedModel.PageMetadata(size, page, tagPage.getTotalElements()));

        pagedModel.add(linkTo(methodOn(TagController.class).getAllTags(page, size)).withSelfRel());

        return ResponseEntity.ok(pagedModel);

    }

    @PostMapping("/new")
    public ResponseEntity<Tag> createTag(@RequestBody TagDTO tagDTO) {
        try {
            Tag createdTag = tagService.createTag(tagDTO.getName());

            createdTag.add(linkTo(methodOn(TagController.class).getTagById(createdTag.getId())).withSelfRel());

            return ResponseEntity.status(HttpStatus.CREATED).body(createdTag);
        } catch (Exception e) {
            throw new BadRequestException("Tag with this name cannot be created");
        }


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Integer id) {
        tagService.deleteTag(id);
        return ResponseEntity.ok().build();
    }


}
