package com.epam.esm.module2boot.dao.jpaDataImpl.fieldResolver;

import java.util.function.Consumer;

public class GenFieldSetter<T> {

    private final Consumer<T> consumer;

    public GenFieldSetter(Consumer<T> consumer) {
        this.consumer = consumer;
    }


    public void setField(Object value) {
        consumer.accept((T)value);
    }
}
