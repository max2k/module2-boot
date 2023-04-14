package com.epam.esm.module2boot.service.impl;

import com.epam.esm.module2boot.dao.GiftCertificateDAO;
import com.epam.esm.module2boot.exception.BadRequestException;
import com.epam.esm.module2boot.model.GiftCertificate;
import com.epam.esm.module2boot.model.Tag;
import com.epam.esm.module2boot.model.dto.GiftCertificateDTO;
import com.epam.esm.module2boot.model.dto.GiftCertificateQueryDTO;
import com.epam.esm.module2boot.model.dto.GiftCertificateUpdateDTO;
import com.epam.esm.module2boot.service.GiftCertificateService;
import com.epam.esm.module2boot.service.TagService;
import com.epam.esm.module2boot.service.Util;
import com.epam.esm.module2boot.validator.GiftCertificateQueryDTOValidator;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDAO giftCertificateDAO;
    private final ModelMapper modelMapper;

    private final TagService tagService;

    private final GiftCertificateQueryDTOValidator giftCertificateQueryDTOValidator;

    public GiftCertificateServiceImpl(GiftCertificateDAO giftCertificateDAO, ModelMapper modelMapper,
                                      TagService tagService,
                                      GiftCertificateQueryDTOValidator giftCertificateQueryDTOValidator) {
        this.giftCertificateDAO = giftCertificateDAO;
        this.modelMapper = modelMapper;
        this.tagService = tagService;
        this.giftCertificateQueryDTOValidator = giftCertificateQueryDTOValidator;
    }

    private static void replaceDateField(String name, Map<String, Object> map) throws ParseException {
        if (map.containsKey(name))
            map.replace(name, Util.parseISO8601(map.get(name).toString()));
    }

    @Override
    public GiftCertificateDTO createGiftCertificate(GiftCertificateDTO giftCertificateDTO) {
        GiftCertificate giftCertificate = modelMapper.map(giftCertificateDTO, GiftCertificate.class);

        if (giftCertificate.getTags() != null && giftCertificate.getTags().size() > 0) {
            Set<Tag> ensuredTags = giftCertificate.getTags().stream()
                    .map(tagService::ensureTag)
                    .collect(Collectors.toSet());
            giftCertificate.setTags(ensuredTags);
        }

        GiftCertificate outGiftCertificate = giftCertificateDAO.createGiftCert(giftCertificate);

        return modelMapper.map(outGiftCertificate, GiftCertificateDTO.class);
    }

    @Override
    public List<GiftCertificateDTO> getGiftCertificatesBy(GiftCertificateQueryDTO giftCertificateQueryDTO) {

        if (!giftCertificateQueryDTOValidator.isValid(giftCertificateQueryDTO))
            throw new IllegalArgumentException("Incoming DTO is not Valid");

        Map<String, Object> queryFields = giftCertificateQueryDTO.getQueryFields();
        List<GiftCertificate> certList = giftCertificateDAO.getAllByParam(queryFields, giftCertificateQueryDTO.getSorting());

        return certList.stream()
                .map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDTO.class))
                .collect(Collectors.toList());

    }

    @Override
    public boolean updateGiftCertificate(int id, GiftCertificateUpdateDTO giftCertificateUpdateDTO) {
        Set<String> allowedFields = Set.of("name", "description", "price", "duration", "create_date", "last_update_date");
        Map<String, String> fields = giftCertificateUpdateDTO.getFields();

        if (fields == null || fields.isEmpty())
            throw new BadRequestException("Field list is empty nothing to do!");

        Map<String, Object> convertedFields = fields.entrySet().stream().collect(
                Collectors.toMap(entry -> entry.getKey().toLowerCase(), Map.Entry::getValue)
        );

        if (!allowedFields.containsAll(fields.keySet()))
            throw new BadRequestException("Check field names, some of them not allowed to be changed");

        try {
            replaceDateField("create_date", convertedFields);
            replaceDateField("last_update_date", convertedFields);
        } catch (ParseException parseException) {
            throw new BadRequestException("Date field format error");
        }

        return giftCertificateDAO.updateGiftCert(id, convertedFields);
    }

    @Override
    public boolean deleteGiftCertificateById(int id) {
        return giftCertificateDAO.deleteGiftCert(id);

    }

    @Override
    public GiftCertificateDTO getGiftCertificateById(int id) {
        try {
            return modelMapper.map(giftCertificateDAO.getGiftCert(id), GiftCertificateDTO.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
