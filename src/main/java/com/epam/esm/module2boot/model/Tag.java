package com.epam.esm.module2boot.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tag {
    public final int NO_ID=-1;
    int id = NO_ID;
    String name;

    public boolean isNoId(){
        return id==NO_ID;
    }
}
