package com.epam.esm.module2boot.controller;

import com.epam.esm.module2boot.model.dto.GiftCertificateDTO;
import com.epam.esm.module2boot.model.dto.GiftCertificateQueryDTO;
import com.epam.esm.module2boot.service.GiftCertificateService;
import com.epam.esm.module2boot.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@RestController
@RequestMapping("/GiftCertificate")
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

    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificateDTO> getGiftCertificateById(@PathVariable int id) {
        GiftCertificateDTO giftCertificateDTO = giftCertificateService.getGiftCertificateById(id);
        if (giftCertificateDTO != null) {
            return ResponseEntity.ok(giftCertificateDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

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
    public ResponseEntity<List<GiftCertificateDTO>> getAllGiftCertificates(
            @RequestParam(name = "tagName", required = false) String tagName,
            @RequestParam(name = "partName", required = false) String partName,
            @RequestParam(name = "partDescription", required = false) String partDesc,
            @RequestParam(name = "sortBy", required = false) String sortOrder1,
            @RequestParam(name = "thenSortBy", required = false) String sortOrder2) {

        Map<String,Object> queryFields=new HashMap<>();
        putIfNotEmpty(tagName,queryFields,"tag.name");
        putIfNotEmpty(partName,queryFields,"gift_certificate.name");
        putIfNotEmpty(partDesc,queryFields,"description");


        List<String> sortFields= Stream.of(sortOrder1,sortOrder2)
                .filter(StringUtils::hasText).toList();

        GiftCertificateQueryDTO giftCertificateQueryDTO=new GiftCertificateQueryDTO();
        giftCertificateQueryDTO.setQueryFields(queryFields);
        giftCertificateQueryDTO.setSorting(sortFields);

        List<GiftCertificateDTO> giftCertificates =
                giftCertificateService.getGiftCertificatesBy(giftCertificateQueryDTO);

        if (!giftCertificates.isEmpty()) {
            return ResponseEntity.ok(giftCertificates);
        } else {
            return ResponseEntity.notFound().build();
         }
       }

    private static void putIfNotEmpty(String value, Map<String, Object> queryFields, String key) {
        if (StringUtils.hasText(value)) queryFields.put(key, value);
    }
}
