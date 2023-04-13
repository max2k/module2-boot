package com.epam.esm.module2boot.dao;

import com.epam.esm.module2boot.model.GiftCertificate;

import java.util.List;
import java.util.Map;

public interface GiftCertificateDAO {
    GiftCertificate createGiftCert(GiftCertificate giftCertificate);
    boolean deleteGiftCert(int id);

    boolean updateGiftCert(int id, Map<String, Object> fieldsToUpdate);
    GiftCertificate getGiftCert(int id);

    List<GiftCertificate> getAllByParam(Map<String,Object> params, List<String> sorting);
}
