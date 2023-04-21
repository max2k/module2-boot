package com.epam.esm.module2boot.dao.jpaDataImpl.fieldResolver;

import java.util.Date;
import java.util.function.Consumer;

public class DateFieldSetter implements FieldSetter{

    private final Consumer<Date> consumer;

    public DateFieldSetter(Consumer<Date> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void setField(Object value) {
        consumer.accept((Date)value);
    }
}
