package com.epam.esm.module2boot.dao.jpaDataImpl.fieldResolver;

import com.epam.esm.module2boot.model.Tag;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TagSetFromDBSetter implements FieldSetter {

    private final Consumer<Set<Tag>> consumer;
    private final Function<String, Integer> getTagIfFunction;


    public TagSetFromDBSetter(Consumer<Set<Tag>> consumer, Function<String, Integer> getTagIdFunction) {
        this.consumer = consumer;
        this.getTagIfFunction = getTagIdFunction;
    }


    @Override
    public void setField(Object value) {
        String tagString = (String) value;
        if (tagString == null) return;
        if (!tagString.isEmpty()) consumer.accept(
                Arrays.stream(tagString.split(","))
                        .map(tagName -> {
                            Tag newTag = new Tag();
                            newTag.setName(tagName);
                            newTag.setId(getTagIfFunction.apply(tagName));
                            return newTag;
                        })
                        .collect(Collectors.toSet())
        );
        else
            consumer.accept(new HashSet<>());
    }
}
