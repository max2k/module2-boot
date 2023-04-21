package com.epam.esm.module2boot.dao.jpaDataImpl.fieldResolver;

import java.util.function.Consumer;

class StrFieldSetter implements FieldSetter {
    final private Consumer<String> consumer;

    StrFieldSetter(Consumer<String> consumer) {
        this.consumer = consumer;
    }

    public void setField(Object value) {
        consumer.accept((String) value);
    }
}
