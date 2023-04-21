package com.epam.esm.module2boot.dao.jpaDataImpl.fieldResolver;

import java.math.BigDecimal;
import java.util.function.Consumer;

public class BigDecimanFieldSetter implements FieldSetter{

    private final Consumer<BigDecimal> consumer;

    public BigDecimanFieldSetter(Consumer<BigDecimal> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void setField(Object value) {
        consumer.accept((BigDecimal) value);
    }
}
