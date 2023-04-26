package com.epam.esm.module2boot.dao.jpaDataImpl.fieldResolver;

import java.util.function.Consumer;

public class GenSetter<T> implements FieldSetter {

    private final Consumer<T> consumer;

    public GenSetter(Consumer<T> consumer) {
        this.consumer = consumer;
    }


    public void setField(Object value) throws ClassCastException {
        consumer.accept((T) value);
    }
}
