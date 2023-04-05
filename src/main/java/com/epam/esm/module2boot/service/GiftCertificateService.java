package com.epam.esm.module2boot.service;

import com.epam.esm.module2boot.model.GiftCertificate;
import com.epam.esm.module2boot.model.dto.GiftCertificateDTO;
import com.epam.esm.module2boot.model.dto.GiftCertificateQueryDTO;
import com.epam.esm.module2boot.model.dto.GiftCertificateSortingDTO;
import com.epam.esm.module2boot.model.dto.GiftCertificateUpdateDTO;

import java.util.List;

public interface GiftCertificateService {

    GiftCertificate createGiftCertificate(GiftCertificateDTO giftCertificateDTO);
    List<GiftCertificate> getGiftCertificatesBy(GiftCertificateQueryDTO giftCertificateQueryDTO,
                                                    GiftCertificateSortingDTO giftCertificateSortingDTO);

    boolean updateGiftCertificate(int id, GiftCertificateUpdateDTO giftCertificateUpdateDTO);
    boolean deleteGiftCertificateById(int id);

    GiftCertificate getGiftCertificateById(int id);
}