package com.epam.esm.module2boot.dao.jpaDataImpl.fieldResolver;

import com.epam.esm.module2boot.exception.BadRequestException;
import com.epam.esm.module2boot.model.GiftCertificate;

import java.util.Map;

public class FieldSetHelper {

    public static void setField(GiftCertificate giftCertificate, String key, Object value) {


        Map<String, FieldSetter> resolverMap =
                Map.of("gift_certificate.name", new GenSetter<>(giftCertificate::setName),
                        "tag.name", new GenSetter<>(giftCertificate::setTags),
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
