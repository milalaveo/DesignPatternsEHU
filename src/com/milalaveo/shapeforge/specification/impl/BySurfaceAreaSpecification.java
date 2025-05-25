package com.milalaveo.shapeforge.specification.impl;

import com.milalaveo.shapeforge.domain.model.Cube;
import com.milalaveo.shapeforge.infrastructure.warehouse.Warehouse;
import com.milalaveo.shapeforge.specification.Specification;

public class BySurfaceAreaSpecification implements Specification<Cube> {
    private final double minSurfaceArea;

    public BySurfaceAreaSpecification(double minSurfaceArea) {
        this.minSurfaceArea = minSurfaceArea;
    }

    @Override
    public boolean isSatisfiedBy(Cube cube) {
        var data = Warehouse.getInstance().get(cube.getId());
        return data != null && data.surfaceArea() >= minSurfaceArea;
    }
}

