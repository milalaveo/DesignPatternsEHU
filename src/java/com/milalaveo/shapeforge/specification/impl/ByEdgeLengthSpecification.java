package com.milalaveo.shapeforge.specification.impl;

import com.milalaveo.shapeforge.domain.model.impl.Cube;
import com.milalaveo.shapeforge.domain.model.Point;
import com.milalaveo.shapeforge.specification.Specification;

public class ByEdgeLengthSpecification implements Specification<Cube> {
    private final double minEdgeLength;

    public ByEdgeLengthSpecification(double minEdgeLength) {
        this.minEdgeLength = minEdgeLength;
    }

    @Override
    public boolean isSatisfiedBy(Cube cube) {
        Point[] points = cube.getPoints();
        double min = Double.MAX_VALUE;

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double dist = distance(points[i], points[j]);
                if (dist > 0 && dist < min) {
                    min = dist;
                }
            }
        }

        return min >= minEdgeLength;
    }

    private double distance(Point a, Point b) {
        double dx = a.getX() - b.getX();
        double dy = a.getY() - b.getY();
        double dz = a.getZ() - b.getZ();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
}
