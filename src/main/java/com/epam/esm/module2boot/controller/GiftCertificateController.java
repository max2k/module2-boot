package com.epam.esm.module2boot.controller;

import com.epam.esm.module2boot.model.GiftCertificate;
import com.epam.esm.module2boot.model.dto.GiftCertificateDTO;
import com.epam.esm.module2boot.service.GiftCertificateService;
import com.epam.esm.module2boot.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/giftCertificates")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;
    private final TagService tagService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService, TagService tagService) {
        this.giftCertificateService = giftCertificateService;
        this.tagService = tagService;
    }

    @PostMapping
    public ResponseEntity<GiftCertificateDTO> createGiftCertificate(@RequestBody GiftCertificateDTO giftCertificateInDTO) {
        GiftCertificateDTO giftCertificateOutDto=
                giftCertificateService.createGiftCertificate(giftCertificateInDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(giftCertificateOutDto);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<GiftCertificate> getGiftCertificateById(@PathVariable int id) {
//        GiftCertificate giftCertificate = giftCertificateService.getGiftCertificateById(id);
//        if (giftCertificate != null) {
//            return ResponseEntity.ok(giftCertificate);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<GiftCertificate> updateGiftCertificate(@PathVariable int id, @RequestBody GiftCertificate giftCertificate) {
//        Set<Tag> tags = giftCertificate.getTags();
//        if (tags != null) {
//            tags = tagService.createTagsIfNotExist(tags);
//            giftCertificate.setTags(tags);
//        }
//        GiftCertificate updatedGiftCertificate = giftCertificateService.updateGiftCertificate(id, giftCertificate);
//        if (updatedGiftCertificate != null) {
//            return ResponseEntity.ok(updatedGiftCertificate);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGiftCertificate(@PathVariable int id) {
        boolean deleted = giftCertificateService.deleteGiftCertificateById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<GiftCertificate>> getAllGiftCertificates(
            @RequestParam(name = "tagName", required = false) String tagName,
            @RequestParam(name = "partName", required = false) String partName,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(name = "sortOrder", required = false, defaultValue = "asc") String sortOrder) {
////        List<GiftCertificate> giftCertificates = giftCertificateService.
////                getGiftCertificates(tagName, partName, sortBy, sortOrder);
//        if (!giftCertificates.isEmpty()) {
//            return ResponseEntity.ok(giftCertificates);
//        } else {
            return ResponseEntity.notFound().build();
//        }
       }
    }
