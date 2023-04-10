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
import org.springframework.dao.EmptyResultDataAccessException;
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
    public GiftCertificateDTO createGiftCertificate(GiftCertificateDTO giftCertificateDTO) {
        GiftCertificate giftCertificate = modelMapper.map(giftCertificateDTO, GiftCertificate.class);

        if ( giftCertificate.getTags() != null && giftCertificate.getTags().size() > 0){
            Set<Tag> ensuredTags = giftCertificate.getTags().stream()
                    .map(tagService::ensureTag)
                    .collect(Collectors.toSet());
            giftCertificate.setTags(ensuredTags);
        }

        GiftCertificate outGiftCertificate=giftCertDAO.createGiftCert(giftCertificate);

        return modelMapper.map(outGiftCertificate,GiftCertificateDTO.class);
    }

    @Override
    public List<GiftCertificateDTO> getGiftCertificatesBy(GiftCertificateQueryDTO giftCertificateQueryDTO) {

        if (! giftCertificateQueryDTOValidator.isValid(giftCertificateQueryDTO))
            throw new IllegalArgumentException("Incoming DTO is not Valid");

        Map<String,Object> queryFields=giftCertificateQueryDTO.getQueryFields();
        List<GiftCertificate> certList=giftCertDAO.getAllByParam(queryFields,giftCertificateQueryDTO.getSorting());

        return certList.stream()
                .map(giftCertificate -> modelMapper.map(giftCertificate,GiftCertificateDTO.class))
                .collect(Collectors.toList());

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
    public GiftCertificateDTO getGiftCertificateById(int id) {
        try {
            return modelMapper.map(giftCertDAO.getGiftCert(id), GiftCertificateDTO.class);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }
}
