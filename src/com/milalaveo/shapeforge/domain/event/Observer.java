package com.milalaveo.shapeforge.domain.event;

import com.milalaveo.shapeforge.domain.model.Cube;

public interface Observer {
    void update(Cube cube);
}
