package com.epam.esm.module2boot.service.impl;

import com.epam.esm.module2boot.Util;
import com.epam.esm.module2boot.model.GiftCertificate;
import com.epam.esm.module2boot.model.dto.GiftCertificateDTO;
import com.epam.esm.module2boot.service.GiftCertificateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GiftCertificateServiceImplTest {

    @Autowired
    GiftCertificateService giftCertificateService;
    @Test
    void createGiftCertificate() throws ParseException {
        GiftCertificateDTO giftCertificateDTO=new GiftCertificateDTO();

        giftCertificateDTO.setId(GiftCertificate.INT_NO_VAL);
        giftCertificateDTO.setName("Test name 11");
        giftCertificateDTO.setDescription("Test description");
        giftCertificateDTO.setDuration(4);
        giftCertificateDTO.setPrice(BigDecimal.valueOf(22));
        giftCertificateDTO.setCreateDate(Util.parseISO8601("2021-01-01T10:11:11"));
        giftCertificateDTO.setLastUpdateDate(Util.parseISO8601("2021-01-01T10:11:11"));

        GiftCertificate result=giftCertificateService.createGiftCertificate(giftCertificateDTO);

        assertNotEquals(GiftCertificate.INT_NO_VAL,result.getId());
        assertEquals("Test name 11",result.getName());

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