package com.epam.esm.module2boot.dao.jpaImpl;

import com.epam.esm.module2boot.dao.GiftCertificateDAO;
import com.epam.esm.module2boot.exception.NotFoundException;
import com.epam.esm.module2boot.model.GiftCertificate;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
@Transactional
@Profile("jpa")
public class GiftCertificateDaoImpl implements GiftCertificateDAO {

    final EntityManager entityManager;

    public GiftCertificateDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public GiftCertificate createGiftCert(GiftCertificate giftCertificate) {

        entityManager.persist(giftCertificate);
        entityManager.flush();
        return entityManager.find(GiftCertificate.class, giftCertificate.getId());

    }

    @Override
    public boolean deleteGiftCert(int id) {
        GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, id);
        entityManager.remove(giftCertificate);
        entityManager.flush();
        return true;
    }

    @Override
    public boolean updateGiftCert(int id, Map<String, Object> fieldsToUpdate) {
        return false;
    }

    @Override
    public GiftCertificate getGiftCert(int id) throws NotFoundException {
        GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, id);
        if (giftCertificate == null)
            throw new NotFoundException("Gift certificate not found with id:" + id);
        return giftCertificate;
    }

    @Override
    public List<GiftCertificate> getAllByParam(Map<String, Object> params, List<String> sorting) {
        return null;
    }
}
