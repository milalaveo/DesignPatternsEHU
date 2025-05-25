package com.milalaveo.shapeforge.specification.impl;

import com.milalaveo.shapeforge.domain.model.Cube;
import com.milalaveo.shapeforge.specification.Specification;

public class ByIdSpecification implements Specification<Cube> {
    private final long targetId;

    public ByIdSpecification(long targetId) {
        this.targetId = targetId;
    }

    @Override
    public boolean isSatisfiedBy(Cube cube) {
        return cube.getId() == targetId;
    }
}