package com.epam.esm.module2boot.dao;

import com.epam.esm.module2boot.model.GiftCertificate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface GiftCertificateDAO {
    GiftCertificate createGiftCert(GiftCertificate giftCertificate);

    boolean deleteGiftCert(int id);

    boolean updateGiftCert(GiftCertificate giftCertificate);

    GiftCertificate getGiftCert(int id);

    Page<GiftCertificate> getAllByParam(Map<String, Object> params, Pageable pageable);
}
