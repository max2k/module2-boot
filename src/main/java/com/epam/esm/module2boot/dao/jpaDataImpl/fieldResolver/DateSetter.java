package com.epam.esm.module2boot.dao.jpaDataImpl.fieldResolver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;

public class DateSetter implements FieldSetter {

    private final Consumer<Date> consumer;

    public DateSetter(Consumer<Date> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void setField(Object value) throws ParseException {
        if (value instanceof Date)
            consumer.accept((Date) value);
        else {
            consumer.accept(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(value.toString()));
        }
    }
}
