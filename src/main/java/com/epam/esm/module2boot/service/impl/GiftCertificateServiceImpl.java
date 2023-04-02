package com.epam.esm.module2boot.service.impl;

import com.epam.esm.module2boot.dao.GiftCertDAO;
import com.epam.esm.module2boot.model.GiftCertificate;
import com.epam.esm.module2boot.model.dto.GiftCertificateDTO;
import com.epam.esm.module2boot.model.dto.GiftCertificateQueryDTO;
import com.epam.esm.module2boot.model.dto.GiftCertificateSortingDTO;
import com.epam.esm.module2boot.model.dto.GiftCertificateUpdateDTO;
import com.epam.esm.module2boot.service.GiftCertificateService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertDAO giftCertDAO;

    public GiftCertificateServiceImpl(GiftCertDAO giftCertDAO) {
        this.giftCertDAO = giftCertDAO;
    }

    @Override
    public GiftCertificate createGiftCertificate(GiftCertificateDTO giftCertificateDTO) {

        return null;
    }

    @Override
    public List<GiftCertificate> getGiftCertificatesBy(GiftCertificateQueryDTO giftCertificateQueryDTO, GiftCertificateSortingDTO giftCertificateSortingDTO) {
        return null;
    }

    @Override
    public boolean updateGiftCertificate(int id, GiftCertificateUpdateDTO giftCertificateUpdateDTO) {
        return false;
    }

    @Override
    public boolean deleteGiftCertificateById(int id) {
        return false;
    }
}
