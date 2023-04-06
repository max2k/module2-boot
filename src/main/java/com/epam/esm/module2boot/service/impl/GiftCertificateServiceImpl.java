package com.epam.esm.module2boot.service.impl;

import com.epam.esm.module2boot.dao.GiftCertDAO;
import com.epam.esm.module2boot.model.GiftCertificate;
import com.epam.esm.module2boot.model.Tag;
import com.epam.esm.module2boot.model.dto.GiftCertificateDTO;
import com.epam.esm.module2boot.model.dto.GiftCertificateQueryDTO;
import com.epam.esm.module2boot.model.dto.GiftCertificateUpdateDTO;
import com.epam.esm.module2boot.service.GiftCertificateService;
import com.epam.esm.module2boot.service.TagService;
import com.epam.esm.module2boot.validator.GiftCertificateQueryDTOValidator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertDAO giftCertDAO;
    private final ModelMapper modelMapper;

    private final TagService tagService;

    private final GiftCertificateQueryDTOValidator giftCertificateQueryDTOValidator;

    public GiftCertificateServiceImpl(GiftCertDAO giftCertDAO, ModelMapper modelMapper,
                                      TagService tagService,
                                      GiftCertificateQueryDTOValidator giftCertificateQueryDTOValidator) {
        this.giftCertDAO = giftCertDAO;
        this.modelMapper = modelMapper;
        this.tagService = tagService;
        this.giftCertificateQueryDTOValidator = giftCertificateQueryDTOValidator;
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
    public List<GiftCertificate> getGiftCertificatesBy(GiftCertificateQueryDTO giftCertificateQueryDTO) {

        if (! giftCertificateQueryDTOValidator.isValid(giftCertificateQueryDTO))
            throw new IllegalArgumentException("Incoming DTO is not Valid");

        Map<String,Object> queryFields=giftCertificateQueryDTO.getQueryFields();

        return giftCertDAO.getAllByParam(queryFields,giftCertificateQueryDTO.getSorting());
    }

    @Override
    public boolean updateGiftCertificate(int id, GiftCertificateUpdateDTO giftCertificateUpdateDTO) {
        Map<String,Object> fields=giftCertificateUpdateDTO.getFields();

        if (fields == null || fields.isEmpty() )
            throw new IllegalArgumentException("Field list is empty nothing to do!");

        return giftCertDAO.updateGiftCert(id,fields);
    }

    @Override
    public boolean deleteGiftCertificateById(int id) {
        return giftCertDAO.deleteGiftCert(id);

    }

    @Override
    public GiftCertificate getGiftCertificateById(int id) {
        return giftCertDAO.getGiftCert(id);
    }
}
