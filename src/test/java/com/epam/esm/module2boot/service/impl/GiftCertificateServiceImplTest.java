package com.epam.esm.module2boot.service.impl;

import com.epam.esm.module2boot.Util;
import com.epam.esm.module2boot.model.GiftCertificate;
import com.epam.esm.module2boot.model.Tag;
import com.epam.esm.module2boot.model.dto.GiftCertificateDTO;
import com.epam.esm.module2boot.model.dto.GiftCertificateUpdateDTO;
import com.epam.esm.module2boot.model.dto.TagDTO;
import com.epam.esm.module2boot.service.GiftCertificateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    void getGiftCertificatesBy() throws ParseException {


    }

    @Test
    void getGifCertificateById() throws ParseException {
        GiftCertificate giftCertificate=giftCertificateService.getGiftCertificateById(1);

        assertNotNull(giftCertificate);
        assertEquals(1,giftCertificate.getId());
        assertEquals("name1",giftCertificate.getName());
        assertEquals("description1",giftCertificate.getDescription());
        assertEquals(100,giftCertificate.getDuration());
        assertEquals(0, new BigDecimal("10.0").compareTo(giftCertificate.getPrice()));
        assertEquals(Util.parseISO8601("2022-05-01T15:30:00"),giftCertificate.getCreateDate());
        assertEquals(Util.parseISO8601("2022-04-01T15:30:00"),giftCertificate.getLastUpdateDate());

        assertEquals(3,giftCertificate.getTags().size());

    }

    @Test
    void updateGiftCertificate() throws ParseException {
        GiftCertificateUpdateDTO updateDTO=new GiftCertificateUpdateDTO();
        updateDTO.setFields(Map.of("name","testname",
                                   "description", "testdescription",
                                   "price", BigDecimal.valueOf(10),
                                   "duration",11,
                                   "last_update_date",Util.parseISO8601("2022-04-01T15:40:00")
                )
        );

        assertTrue(giftCertificateService.updateGiftCertificate(1, updateDTO));

        GiftCertificate giftCertificate=giftCertificateService.getGiftCertificateById(1);

        assertEquals("testname", giftCertificate.getName() );
        assertEquals("testdescription", giftCertificate.getDescription() );
        assertEquals(0, giftCertificate.getPrice().compareTo(BigDecimal.valueOf(10)) );
        assertEquals(11,giftCertificate.getDuration() );
        assertEquals( Util.parseISO8601("2022-04-01T15:40:00"), giftCertificate.getLastUpdateDate() );
    }

    @Test
    void updateGiftCertificateWrongId() {
        GiftCertificateUpdateDTO updateDTO=new GiftCertificateUpdateDTO();
        updateDTO.setFields(Map.of("name","testname"));

        assertFalse(giftCertificateService.updateGiftCertificate(33,updateDTO));
    }

    @ParameterizedTest
    @MethodSource("wrongMap")
    void updateGiftCertificateWrongVal(Map<String,Object> fields, Class<Throwable> exceptionClass) {
        GiftCertificateUpdateDTO updateDTO=new GiftCertificateUpdateDTO();
        updateDTO.setFields(fields);

        assertThrows(exceptionClass, () ->
            giftCertificateService.updateGiftCertificate(1,updateDTO)
        );

    }

    public static Stream<Arguments> wrongMap() {
        return Stream.of(
                Arguments.of(null,IllegalArgumentException.class),
                Arguments.of( new HashMap<String,Object>(),IllegalArgumentException.class),
                Arguments.of(Map.of("WrongName","ingnored"), BadSqlGrammarException.class)
        );
    }

    @Test
    void deleteGiftCertificateById() {
        assertTrue( giftCertificateService.deleteGiftCertificateById(1));
        assertFalse( giftCertificateService.deleteGiftCertificateById(1));
        assertThrows(EmptyResultDataAccessException.class,() ->
            giftCertificateService.getGiftCertificateById(1)
        );
    }
}