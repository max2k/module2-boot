package com.epam.esm.module2boot.dao.jpaDataImpl.fieldResolver;

import java.util.function.Consumer;

class IntFieldSetter implements FieldSetter {
    final private Consumer<Integer> consumer;

    IntFieldSetter(Consumer<Integer> consumer) {
        this.consumer = consumer;
    }

    public void setField(Object value) {
        consumer.accept((Integer) value);
    }
}
