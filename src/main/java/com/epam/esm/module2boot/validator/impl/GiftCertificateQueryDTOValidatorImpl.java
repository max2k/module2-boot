package com.epam.esm.module2boot.validator.impl;

import com.epam.esm.module2boot.model.dto.GiftCertificateQueryDTO;
import com.epam.esm.module2boot.validator.GiftCertificateQueryDTOValidator;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class GiftCertificateQueryDTOValidatorImpl implements GiftCertificateQueryDTOValidator {
    Set<String> allowedFields = Set.of("tag.name", "description", "gift_certificate.name");
    Set<String> sortDirs = Set.of("", " asc", " desc");
    Set<String> allowedSorting =
            Stream.of("gift_certificate.name", "create_date")
                    .flatMap(s -> sortDirs.stream().map(s1 -> s + s1))
                    .collect(Collectors.toSet());

    @Override
    public boolean isValid(GiftCertificateQueryDTO giftCertificateQueryDTO) {

        if ((giftCertificateQueryDTO.getQueryFields() != null) &&
                !giftCertificateQueryDTO.getQueryFields().isEmpty()) {

            Set<String> inFields = giftCertificateQueryDTO.getQueryFields().keySet()
                    .stream().map(String::toLowerCase).collect(Collectors.toSet());

            if (!allowedFields.containsAll(inFields)) return false;
        }

        if (giftCertificateQueryDTO.getSorting() != null &&
                !giftCertificateQueryDTO.getSorting().isEmpty()) {

            Set<String> inSorting = giftCertificateQueryDTO.getSorting().stream()
                    .map(String::toLowerCase).collect(Collectors.toSet());

            if (!allowedSorting.containsAll(inSorting)) return false;
        }

        return true;
    }
}
