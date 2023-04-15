package com.epam.esm.module2boot.validator.impl;

import com.epam.esm.module2boot.model.dto.GiftCertificateQueryDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GiftCertificateQueryDTOValidatorImplTest {


    private GiftCertificateQueryDTOValidatorImpl giftCertificateQueryDTOValidator;

    public static Stream<Arguments> queryData() {
        return Stream.of(
                Arguments.of(null, null, true)
                , Arguments.of(new HashMap<String, Object>(), new LinkedList<>(), true)
                , Arguments.of(Map.of("tag.name", ""), null, true)
                , Arguments.of(Map.of("tag.Name", ""), null, true)
                , Arguments.of(Map.of("1", ""), null, false)
                , Arguments.of(null, List.of("ddd desc"), false)
                , Arguments.of(null, List.of("create_date"), true)
                , Arguments.of(null, List.of("create_date asc"), true)
                , Arguments.of(null, List.of("create_date desc"), true)
        );
    }

    @BeforeEach
    void setUp() {
        giftCertificateQueryDTOValidator = new GiftCertificateQueryDTOValidatorImpl();
    }

    @ParameterizedTest
    @MethodSource("queryData")
    void isValid(Map<String, Object> queryFields, List<String> sorting, boolean result) {
        GiftCertificateQueryDTO giftCertificateQueryDTO = new GiftCertificateQueryDTO();
        giftCertificateQueryDTO.setQueryFields(queryFields);
        giftCertificateQueryDTO.setSorting(sorting);

        assertEquals(result, giftCertificateQueryDTOValidator.isValid(giftCertificateQueryDTO));
    }

}