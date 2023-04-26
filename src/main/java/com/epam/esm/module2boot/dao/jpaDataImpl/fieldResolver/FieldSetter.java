package com.epam.esm.module2boot.dao.jpaDataImpl.fieldResolver;

import java.text.ParseException;

public interface FieldSetter {
    void setField(Object value) throws ParseException;
}
