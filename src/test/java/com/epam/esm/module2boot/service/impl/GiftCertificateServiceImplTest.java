package com.epam.esm.module2boot.service.impl;

import com.epam.esm.module2boot.Util;
import com.epam.esm.module2boot.model.GiftCertificate;
import com.epam.esm.module2boot.model.Tag;
import com.epam.esm.module2boot.model.dto.GiftCertificateDTO;
import com.epam.esm.module2boot.model.dto.TagDTO;
import com.epam.esm.module2boot.service.GiftCertificateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class GiftCertificateServiceImplTest {

    @Autowired
    GiftCertificateService giftCertificateService;
    @Test
    void createGiftCertificate() throws ParseException {
        GiftCertificateDTO giftCertificateDTO = getTestGiftCertificateDTO();

        giftCertificateDTO.setTagsDTO( genTags("GS tst tag1","GS tst tag2","GS tst tag3","tag1") );

        GiftCertificate result=giftCertificateService.createGiftCertificate(giftCertificateDTO);

        GiftCertificate dbResult=giftCertificateService.getGiftCertificateById(result.getId());

        assertNotEquals(GiftCertificate.INT_NO_VAL,result.getId());
        assertEquals(result.getId(),dbResult.getId());

        assertEquals(0, compareGS(result,dbResult));

        // all tags have valid id
        assertTrue(
                dbResult.getTags().stream().noneMatch(Tag::isNoId)
        );

        // existing tag have valid
        assertEquals(1,
            dbResult.getTags().stream().filter(tag -> tag.getName().equals("tag1") && tag.getId()==1).count()
        );

    }

    @Test
    void createGiftCertificateNoTags() throws ParseException {
        GiftCertificateDTO giftCertificateDTO = getTestGiftCertificateDTO();

        GiftCertificate result=giftCertificateService.createGiftCertificate(giftCertificateDTO);

        GiftCertificate dbResult=giftCertificateService.getGiftCertificateById(result.getId());

        assertNotEquals(GiftCertificate.INT_NO_VAL,result.getId());
        assertEquals(result.getId(),dbResult.getId());

        assertEquals(0, compareGS(result,dbResult));
    }


    private static GiftCertificateDTO getTestGiftCertificateDTO() throws ParseException {
        GiftCertificateDTO giftCertificateDTO=new GiftCertificateDTO();

        giftCertificateDTO.setId(GiftCertificate.INT_NO_VAL);
        giftCertificateDTO.setName("Test name 11");
        giftCertificateDTO.setDescription("Test description");
        giftCertificateDTO.setDuration(4);
        giftCertificateDTO.setPrice(BigDecimal.valueOf(22));
        giftCertificateDTO.setCreateDate(Util.parseISO8601("2021-01-01T10:11:11"));
        giftCertificateDTO.setLastUpdateDate(Util.parseISO8601("2021-01-01T10:11:11"));
        return giftCertificateDTO;
    }

    private int compareGS(GiftCertificate result, GiftCertificate dbResult) {
        return Comparator
                .comparing(GiftCertificate::getName)
                .thenComparing(GiftCertificate::getDescription)
                .thenComparing(GiftCertificate::getDuration)
                .thenComparing(GiftCertificate::getPrice)
                .thenComparing(GiftCertificate::getCreateDate)
                .thenComparing(GiftCertificate::getLastUpdateDate)
                .compare(result,dbResult);
    }

    private static     Set<TagDTO> genTags(String...args) {
        return Arrays.stream(args).map(s ->
            TagDTO.builder().name(s).build()
        ).collect(Collectors.toSet());
    }

    @Test
    void getGiftCertificatesBy() {

    }

    @Test
    void updateGiftCertificate() {
    }

    @Test
    void deleteGiftCertificateById() {
    }
}