package com.epam.esm.module2boot.service;

import com.epam.esm.module2boot.model.dto.GiftCertificateDTO;
import com.epam.esm.module2boot.model.dto.GiftCertificateQueryDTO;
import com.epam.esm.module2boot.model.dto.GiftCertificateUpdateDTO;

import java.util.List;

public interface GiftCertificateService {

    GiftCertificateDTO createGiftCertificate(GiftCertificateDTO giftCertificateDTO);

    List<GiftCertificateDTO> getGiftCertificatesBy(GiftCertificateQueryDTO giftCertificateQueryDTO);

    boolean updateGiftCertificate(int id, GiftCertificateUpdateDTO giftCertificateUpdateDTO);

    boolean deleteGiftCertificateById(int id);

    GiftCertificateDTO getGiftCertificateById(int id);
}
