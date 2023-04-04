package com.epam.esm.module2boot.service.impl;

import com.epam.esm.module2boot.dao.GiftCertDAO;
import com.epam.esm.module2boot.model.GiftCertificate;
import com.epam.esm.module2boot.model.Tag;
import com.epam.esm.module2boot.model.dto.GiftCertificateDTO;
import com.epam.esm.module2boot.model.dto.GiftCertificateQueryDTO;
import com.epam.esm.module2boot.model.dto.GiftCertificateSortingDTO;
import com.epam.esm.module2boot.model.dto.GiftCertificateUpdateDTO;
import com.epam.esm.module2boot.service.GiftCertificateService;
import com.epam.esm.module2boot.service.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertDAO giftCertDAO;
    private final ModelMapper modelMapper;

    private final TagService tagService;

    public GiftCertificateServiceImpl(GiftCertDAO giftCertDAO, ModelMapper modelMapper, TagService tagService) {
        this.giftCertDAO = giftCertDAO;
        this.modelMapper = modelMapper;
        this.tagService = tagService;
    }

    @Override
    public GiftCertificate createGiftCertificate(GiftCertificateDTO giftCertificateDTO) {
        GiftCertificate giftCertificate = modelMapper.map(giftCertificateDTO, GiftCertificate.class);

        if ( giftCertificate.getTags() != null && giftCertificate.getTags().size() > 0){
            Set<Tag> ensuredTags = giftCertificate.getTags().stream()
                    .map(tagService::ensureTag)
                    .collect(Collectors.toSet());
            giftCertificate.setTags(ensuredTags);
        }

        giftCertDAO.createGiftCert(giftCertificate);

        return giftCertDAO.createGiftCert(giftCertificate);
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
