package com.epam.esm.module2boot.dao.jpaDataImpl.fieldResolver;

import com.epam.esm.module2boot.exception.BadRequestException;
import com.epam.esm.module2boot.model.GiftCertificate;

import java.util.Map;

public class FieldSetHelper {

    public static void setField(GiftCertificate giftCertificate, String key, Object value) {


        Map<String, GenFieldSetter<?>> resolverMap =
                Map.of("gift_certificate.name", new GenFieldSetter<>(giftCertificate::setName),
                        "tag.name", new GenFieldSetter<>(giftCertificate::setTags),
                        "name", new GenFieldSetter<>(giftCertificate::setName),
                        "description", new GenFieldSetter<>(giftCertificate::setDescription),
                        "duration", new GenFieldSetter<>(giftCertificate::setDuration),
                        "price", new GenFieldSetter<>(giftCertificate::setPrice),
                        "create_date", new GenFieldSetter<>(giftCertificate::setCreateDate),
                        "last_update_date", new GenFieldSetter<>(giftCertificate::setLastUpdateDate)
                );

        GenFieldSetter<?> fieldSetter = resolverMap.get(key);
        if (fieldSetter == null) throw new BadRequestException("Not found field:" + key);

        fieldSetter.setField(value);
    }
}
