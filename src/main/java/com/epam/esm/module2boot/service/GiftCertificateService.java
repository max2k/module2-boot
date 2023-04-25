package com.epam.esm.module2boot.service;

import com.epam.esm.module2boot.dto.GiftCertificateDTO;
import com.epam.esm.module2boot.dto.GiftCertificateQueryDTO;
import com.epam.esm.module2boot.dto.GiftCertificateUpdateDTO;
import com.epam.esm.module2boot.exception.NotFoundException;
import com.epam.esm.module2boot.exception.dao.DataBaseConstrainException;
import com.epam.esm.module2boot.model.GiftCertificate;

import java.util.List;

public interface GiftCertificateService {

    GiftCertificateDTO createGiftCertificate(GiftCertificateDTO giftCertificateDTO) throws DataBaseConstrainException;

    List<GiftCertificateDTO> getGiftCertificatesBy(GiftCertificateQueryDTO giftCertificateQueryDTO);

    boolean updateGiftCertificate(int id, GiftCertificateUpdateDTO giftCertificateUpdateDTO);

    boolean deleteGiftCertificateById(int id);

    GiftCertificateDTO getGiftCertificateDTOById(int id) throws NotFoundException;

    GiftCertificate getGiftCertificateById(int id) throws NotFoundException;
}
