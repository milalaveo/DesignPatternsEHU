package com.milalaveo.shapeforge.specification.impl;

import com.milalaveo.shapeforge.domain.model.impl.Cube;
import com.milalaveo.shapeforge.infrastructure.warehouse.Warehouse;
import com.milalaveo.shapeforge.specification.Specification;

public class ByVolumeSpecification implements Specification<Cube> {
    private final double minVolume;

    public ByVolumeSpecification(double minVolume) {
        this.minVolume = minVolume;
    }

    @Override
    public boolean isSatisfiedBy(Cube cube) {
        var data = Warehouse.getInstance().get(cube.getId());
        return data != null && data.volume() >= minVolume;
    }
}
