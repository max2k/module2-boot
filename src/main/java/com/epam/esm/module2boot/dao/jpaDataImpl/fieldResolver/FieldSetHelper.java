package com.epam.esm.module2boot.dao.jpaDataImpl.fieldResolver;

import com.epam.esm.module2boot.exception.BadRequestException;
import com.epam.esm.module2boot.model.GiftCertificate;
import com.epam.esm.module2boot.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@AllArgsConstructor
public class FieldSetHelper {

    private TagService tagService;

    public void setField(GiftCertificate giftCertificate, String key, Object value) {


        Map<String, FieldSetter> resolverMap =
                Map.of("gift_certificate.name", new GenSetter<>(giftCertificate::setName),
                        "tags", new TagSetFromDBSetter(giftCertificate::setTags, tagService::getTagIdByName),
                        "name", new GenSetter<>(giftCertificate::setName),
                        "description", new GenSetter<>(giftCertificate::setDescription),
                        "duration", new IntSetter(giftCertificate::setDuration),
                        "price", new BigDecimalSetter(giftCertificate::setPrice),
                        "create_date", new DateSetter(giftCertificate::setCreateDate),
                        "last_update_date", new DateSetter(giftCertificate::setLastUpdateDate)
                );

        FieldSetter fieldSetter = resolverMap.get(key);

        if (fieldSetter == null) throw new BadRequestException("Not found field:" + key);

        try {
            fieldSetter.setField(value);
        } catch (Exception e) {
            throw new BadRequestException("Not correct value for field:" + key, e);
        }

    }
}
