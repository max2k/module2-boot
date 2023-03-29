package com.epam.esm.module2boot.dao;

import com.epam.esm.module2boot.model.GiftCertificate;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface GiftCertDAO {
    void createGiftCert(GiftCertificate giftCertificate);
    void deleteGiftCert(int id);

    void updateGiftCert(int id, Map<String, Objects> fieldsToUpdate);
    void getGiftCert(int id);

    List<GiftCertificate> getAllByParam(Map<String,Object> params, Map<String,String> sorting);
}
