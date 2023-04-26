package com.epam.esm.module2boot.service;

import com.epam.esm.module2boot.dto.GiftCertificateDTO;
import com.epam.esm.module2boot.dto.GiftCertificateUpdateDTO;
import com.epam.esm.module2boot.exception.NotFoundException;
import com.epam.esm.module2boot.exception.dao.DataBaseConstrainException;
import com.epam.esm.module2boot.model.GiftCertificate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface GiftCertificateService {

    GiftCertificateDTO createGiftCertificate(GiftCertificateDTO giftCertificateDTO) throws DataBaseConstrainException;

    Page<GiftCertificateDTO> getGiftCertificatesBy(Map<String, Object> params, Pageable pageable);

    boolean updateGiftCertificate(int id, GiftCertificateUpdateDTO giftCertificateUpdateDTO);

    boolean deleteGiftCertificateById(int id);

    GiftCertificateDTO getGiftCertificateDTOById(int id) throws NotFoundException;

    GiftCertificate getGiftCertificateById(int id) throws NotFoundException;
}
