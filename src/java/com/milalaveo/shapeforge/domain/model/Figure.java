package com.milalaveo.shapeforge.domain.model;

import com.milalaveo.shapeforge.domain.event.Observable;

public interface Figure extends Observable {
    long getId();

    void setId(long id);

    String getName();

    @Override
    String toString();

    @Override
    boolean equals(Object other);

    @Override
    int hashCode();
}
