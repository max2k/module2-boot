package com.epam.esm.module2boot.dao.jpaDataImpl.fieldResolver;

import java.util.function.Consumer;

public class IntSetter implements FieldSetter {

    private final Consumer<Integer> consumer;


    public IntSetter(Consumer<Integer> consumer) {
        this.consumer = consumer;
    }


    @Override
    public void setField(Object value) {
        consumer.accept(Integer.valueOf(value.toString()));
    }
}
