package com.milalaveo.shapeforge.specification.impl;

import com.milalaveo.shapeforge.domain.model.Cube;
import com.milalaveo.shapeforge.domain.model.Point;
import com.milalaveo.shapeforge.specification.Specification;

public class ByPositionSpecification implements Specification<Cube> {
    private final double zThreshold;

    public ByPositionSpecification(double zThreshold) {
        this.zThreshold = zThreshold;
    }

    @Override
    public boolean isSatisfiedBy(Cube cube) {
        for (Point p : cube.getPoints()) {
            if (p.getZ() > zThreshold) {
                return true;
            }
        }
        return false;
    }
}
