package com.epam.esm.module2boot.dao.jpaImpl;

import com.epam.esm.module2boot.dao.GiftCertificateDAO;
import com.epam.esm.module2boot.model.GiftCertificate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
@Transactional
@Profile("jpa")
public class GiftCertificateDaoImpl implements GiftCertificateDAO {
    @Override
    public GiftCertificate createGiftCert(GiftCertificate giftCertificate) {
        return null;
    }

    @Override
    public boolean deleteGiftCert(int id) {
        return false;
    }

    @Override
    public boolean updateGiftCert(int id, Map<String, Object> fieldsToUpdate) {
        return false;
    }

    @Override
    public GiftCertificate getGiftCert(int id) {
        return null;
    }

    @Override
    public List<GiftCertificate> getAllByParam(Map<String, Object> params, List<String> sorting) {
        return null;
    }
}
