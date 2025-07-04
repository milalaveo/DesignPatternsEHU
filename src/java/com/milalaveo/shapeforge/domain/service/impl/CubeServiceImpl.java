package com.milalaveo.shapeforge.domain.service.impl;

import com.milalaveo.shapeforge.domain.model.impl.Cube;
import com.milalaveo.shapeforge.domain.model.Point;
import com.milalaveo.shapeforge.domain.service.CubeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CubeServiceImpl implements CubeService {
    private static final Logger logger = LoggerFactory.getLogger(CubeService.class);

    // check the edge length  (minimal distance between points)
    private static double edgeLength(Cube cube) {
        Point[] points = cube.getPoints();
        double min = Double.MAX_VALUE;

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double dist = distance(points[i], points[j]);
                if (dist < min && dist > 0.0) {
                    min = dist;
                }
            }
        }

        return min;
    }

    @Override
    public double calculateVolume(Cube cube) {
        double a = edgeLength(cube);
        double volume = a * a * a;
        logger.debug("Calculated volume: {}", volume);
        return volume;
    }

    @Override
    public double calculateSurfaceArea(Cube cube) {
        double a = edgeLength(cube);
        return 6 * a * a;
    }

    private static double distance(Point a, Point b) {
        double dx = a.getX() - b.getX();
        double dy = a.getY() - b.getY();
        double dz = a.getZ() - b.getZ();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
}
