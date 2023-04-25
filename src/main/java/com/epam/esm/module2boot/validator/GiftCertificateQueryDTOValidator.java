package com.epam.esm.module2boot.validator;

import com.epam.esm.module2boot.dto.GiftCertificateQueryDTO;

public interface GiftCertificateQueryDTOValidator {
    boolean isValid(GiftCertificateQueryDTO giftCertificateDTO);
}
