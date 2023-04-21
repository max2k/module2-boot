package com.epam.esm.module2boot.dao.jpaDataImpl.fieldResolver;

import com.epam.esm.module2boot.model.Tag;

import java.util.Set;
import java.util.function.Consumer;

public class TagFieldSetter implements FieldSetter{
    private  final Consumer<Set<Tag>> consumer;

    public TagFieldSetter(Consumer<Set<Tag>> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void setField(Object value) {
        consumer.accept((Set<Tag>) value);
    }
}
