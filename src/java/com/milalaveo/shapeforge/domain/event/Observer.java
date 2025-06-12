package com.milalaveo.shapeforge.domain.event;

import com.milalaveo.shapeforge.domain.model.impl.Cube;

public interface Observer {
    void update(Cube cube);
}
