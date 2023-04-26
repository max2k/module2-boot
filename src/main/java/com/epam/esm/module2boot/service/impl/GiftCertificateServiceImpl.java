package com.epam.esm.module2boot.service.impl;

import com.epam.esm.module2boot.dao.GiftCertificateDAO;
import com.epam.esm.module2boot.dto.GiftCertificateDTO;
import com.epam.esm.module2boot.dto.GiftCertificateUpdateDTO;
import com.epam.esm.module2boot.exception.BadRequestException;
import com.epam.esm.module2boot.exception.NotFoundException;
import com.epam.esm.module2boot.model.GiftCertificate;
import com.epam.esm.module2boot.service.GiftCertificateService;
import com.epam.esm.module2boot.service.TagService;
import com.epam.esm.module2boot.service.Util;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDAO giftCertificateDAO;
    private final ModelMapper modelMapper;

    private final TagService tagService;


    private static void replaceDateField(String name, Map<String, Object> map) throws ParseException {
        if (map.containsKey(name))
            map.replace(name, Util.parseISO8601(map.get(name).toString()));
    }

    @Override
    public GiftCertificateDTO createGiftCertificate(GiftCertificateDTO giftCertificateDTO) {
        GiftCertificate giftCertificate = modelMapper.map(giftCertificateDTO, GiftCertificate.class);

        if (giftCertificate.getTags() != null && giftCertificate.getTags().size() > 0) {

            giftCertificate.setTags(giftCertificate.getTags()
                    .stream()
                    .map(tagService::ensureTag)
                    .collect(Collectors.toSet()));
        }

        GiftCertificate outGiftCertificate = giftCertificateDAO.createGiftCert(giftCertificate);

        return modelMapper.map(outGiftCertificate, GiftCertificateDTO.class);
    }

    @Override
    public Page<GiftCertificateDTO> getGiftCertificatesBy(Map<String, Object> queryFields, Pageable pageable) {

        Page<GiftCertificate> certList =
                giftCertificateDAO.getAllByParam(queryFields, pageable);

        return certList.map(giftCertificate -> modelMapper.map(giftCertificate, GiftCertificateDTO.class));

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
    public GiftCertificateDTO getGiftCertificateDTOById(int id) throws NotFoundException {
        return modelMapper.map(giftCertificateDAO.getGiftCert(id), GiftCertificateDTO.class);
    }

    @Override
    public GiftCertificate getGiftCertificateById(int id) throws NotFoundException {
        return giftCertificateDAO.getGiftCert(id);

    }
}
