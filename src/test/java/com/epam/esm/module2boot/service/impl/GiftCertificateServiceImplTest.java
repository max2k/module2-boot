package com.epam.esm.module2boot.service.impl;

import com.epam.esm.module2boot.converter.GetAllCertParamsToQueryMapConverter;
import com.epam.esm.module2boot.dto.GiftCertificateDTO;
import com.epam.esm.module2boot.dto.GiftCertificateUpdateDTO;
import com.epam.esm.module2boot.dto.TagDTO;
import com.epam.esm.module2boot.exception.BadRequestException;
import com.epam.esm.module2boot.exception.NotFoundException;
import com.epam.esm.module2boot.exception.dao.DataBaseConstrainException;
import com.epam.esm.module2boot.model.GiftCertificate;
import com.epam.esm.module2boot.service.GiftCertificateService;
import com.epam.esm.module2boot.service.Util;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

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

    private static GiftCertificateDTO getTestGiftCertificateDTO() throws ParseException {
        GiftCertificateDTO giftCertificateDTO = new GiftCertificateDTO();

        giftCertificateDTO.setId(GiftCertificate.INT_NO_VAL);
        giftCertificateDTO.setName("Test name 11");
        giftCertificateDTO.setDescription("Test description");
        giftCertificateDTO.setDuration(4);
        giftCertificateDTO.setPrice(BigDecimal.valueOf(22));
        giftCertificateDTO.setCreateDate(Util.parseISO8601("2021-01-01T10:11:11"));
        giftCertificateDTO.setLastUpdateDate(Util.parseISO8601("2021-01-01T10:11:11"));
        return giftCertificateDTO;
    }

    private static Set<TagDTO> genTags(String... args) {
        return Arrays.stream(args).map(s ->
                TagDTO.builder().name(s).build()
        ).collect(Collectors.toSet());
    }

    public static Stream<Arguments> emptyQueries() {
        return Stream.of(
                Arguments.of(null, new int[]{1, 2, 3, 4, 5, 6}, null)
                , Arguments.of(new HashMap<>(), new int[]{1, 2, 3, 4, 5, 6}, new LinkedList<>())
                , Arguments.of(Map.of("tags", "tag1"), new int[]{1, 2}, null)
                , Arguments.of(
                        Map.of("description", "description2")
                        , new int[]{2, 4, 5, 6}, null)
                , Arguments.of(
                        Map.of("description", "description2"
                                , "tags", "tag1")
                        , new int[]{2}, null)
                , Arguments.of(
                        Map.of("description", "description%"
                                , "tags", "tag1")
                        , new int[]{1, 2}, null)

                , Arguments.of(null, new int[]{6, 5, 4, 3, 2, 1}
                        , List.of("name,desc"))
                , Arguments.of(null, new int[]{3717, 355, 5206, 5931, 3378, 4451}
                        , List.of("createDate", "name, desc"))
                , Arguments.of(Map.of("tags", "tag1"), new int[]{2, 1}
                        , List.of("createDate", "name, desc"))
        );
    }

    public static Stream<Arguments> wrongMap() {
        return Stream.of(
                Arguments.of(null, BadRequestException.class),
                Arguments.of(new HashMap<String, Object>(), BadRequestException.class),
                Arguments.of(Map.of("WrongName", "ignored"), BadRequestException.class)
        );
    }

    @Test
    void createGiftCertificate() throws ParseException, DataBaseConstrainException {
        GiftCertificateDTO giftCertificateDTO = getTestGiftCertificateDTO();

        giftCertificateDTO.setTags(genTags("GS tst tag1", "GS tst tag2", "GS tst tag3", "tag2"));

        GiftCertificateDTO result = giftCertificateService.createGiftCertificate(giftCertificateDTO);

        GiftCertificateDTO dbResult = giftCertificateService.getGiftCertificateDTOById(result.getId());

        assertNotEquals(GiftCertificate.INT_NO_VAL, result.getId());
        assertEquals(result.getId(), dbResult.getId());

        assertEquals(0, compareGS(result, dbResult));

        // all tags have valid id
        assertTrue(
                dbResult.getTags().stream().noneMatch(tagDTO -> tagDTO.getId() <= 0)
        );

        // existing tag have valid
        assertEquals(1,
                dbResult.getTags().stream().filter(tag -> tag.getName().equals("tag2") && tag.getId() == 1).count()
        );

    }

    @Test
    void createGiftCertificateNoTags() throws ParseException, DataBaseConstrainException {
        GiftCertificateDTO giftCertificateDTO = getTestGiftCertificateDTO();

        GiftCertificateDTO result = giftCertificateService.createGiftCertificate(giftCertificateDTO);

        GiftCertificateDTO dbResult = giftCertificateService.getGiftCertificateDTOById(result.getId());

        assertNotEquals(GiftCertificate.INT_NO_VAL, result.getId());
        assertEquals(result.getId(), dbResult.getId());

        assertEquals(0, compareGS(result, dbResult));
    }

    private int compareGS(GiftCertificateDTO result, GiftCertificateDTO dbResult) {
        return Comparator
                .comparing(GiftCertificateDTO::getName)
                .thenComparing(GiftCertificateDTO::getDescription)
                .thenComparing(GiftCertificateDTO::getDuration)
                .thenComparing(GiftCertificateDTO::getPrice)
                .thenComparing(GiftCertificateDTO::getCreateDate)
                .thenComparing(GiftCertificateDTO::getLastUpdateDate)
                .compare(result, dbResult);
    }

    @ParameterizedTest
    @MethodSource("emptyQueries")
    void getGiftCertificatesByEmptyData(Map<String, Object> queryFields
            , int[] resultIds, List<String> sorting) {

        MultiValueMap<String, String> queryMap = new LinkedMultiValueMap<>();
        if (sorting != null && sorting.size() > 0) sorting.forEach(s -> queryMap.add("sort", s));

        GetAllCertParamsToQueryMapConverter converter = new GetAllCertParamsToQueryMapConverter(queryMap);

        Page<GiftCertificateDTO> certs = giftCertificateService.getGiftCertificatesBy(queryFields,
                PageRequest.of(0, 6, converter.getSort()));


        Stream<Integer> idSet = certs.stream().map(GiftCertificateDTO::getId);

        if (sorting != null && !sorting.isEmpty()) {
            int[] arr = idSet.mapToInt(Integer::intValue).toArray();
            System.out.println(Arrays.toString(arr));
            assertArrayEquals(resultIds, arr);
        } else {
            assertEquals(Arrays.stream(resultIds).boxed().collect(Collectors.toSet()),
                    idSet.collect(Collectors.toSet()));
        }


    }

    @Test
    void getGifCertificateById() throws ParseException {
        GiftCertificateDTO giftCertificateDto = giftCertificateService.getGiftCertificateDTOById(1);

        assertNotNull(giftCertificateDto);
        assertEquals(1, giftCertificateDto.getId());
        assertEquals("name1", giftCertificateDto.getName());
        assertEquals("description1", giftCertificateDto.getDescription());
        assertEquals(100, giftCertificateDto.getDuration());
        assertEquals(0, new BigDecimal("10.0").compareTo(giftCertificateDto.getPrice()));
        assertEquals(Util.parseISO8601("2022-05-01T15:30:00"), giftCertificateDto.getCreateDate());
        assertEquals(Util.parseISO8601("2022-04-01T15:30:00"), giftCertificateDto.getLastUpdateDate());

        assertEquals(4, giftCertificateDto.getTags().size());

    }

    @Test
    void updateGiftCertificate() throws ParseException {
        GiftCertificateUpdateDTO updateDTO = new GiftCertificateUpdateDTO();
        updateDTO.setFields(Map.of("name", "test name",
                        "description", "test description",
                        "price", "10",
                        "duration", "11",
                        "last_update_date", "2022-04-01T15:40:00"
                )
        );

        assertTrue(giftCertificateService.updateGiftCertificate(1, updateDTO));

        GiftCertificateDTO giftCertificateDto = giftCertificateService.getGiftCertificateDTOById(1);

        assertEquals("test name", giftCertificateDto.getName());
        assertEquals("test description", giftCertificateDto.getDescription());
        assertEquals(0, giftCertificateDto.getPrice().compareTo(BigDecimal.valueOf(10)));
        assertEquals(11, giftCertificateDto.getDuration());
        assertEquals(Util.parseISO8601("2022-04-01T15:40:00"), giftCertificateDto.getLastUpdateDate());
    }

    @Test
    void updateGiftCertificateWrongId() {
        GiftCertificateUpdateDTO updateDTO = new GiftCertificateUpdateDTO();
        updateDTO.setFields(Map.of("name", "testname"));

        assertThrows(NotFoundException.class,
                () -> giftCertificateService.updateGiftCertificate(100033, updateDTO));
    }

    @ParameterizedTest
    @MethodSource("wrongMap")
    void updateGiftCertificateWrongVal(Map<String, String> fields, Class<Throwable> exceptionClass) {
        GiftCertificateUpdateDTO updateDTO = new GiftCertificateUpdateDTO();
        updateDTO.setFields(fields);

        assertThrows(exceptionClass, () ->
                giftCertificateService.updateGiftCertificate(1, updateDTO)
        );

    }

    @Test
    void deleteGiftCertificateById() {
        assertTrue(giftCertificateService.deleteGiftCertificateById(1));
        assertThrows(NotFoundException.class, () -> giftCertificateService.getGiftCertificateDTOById(1));
    }
}