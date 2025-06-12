package com.milalaveo.shapeforge.domain.service;

import com.milalaveo.shapeforge.domain.model.impl.Cube;

public interface CubeService {
    double calculateVolume(Cube cube);

    double calculateSurfaceArea(Cube cube);
}
