package com.milalaveo.shapeforge.domain.validator;

import com.milalaveo.shapeforge.domain.model.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class CubeValidator {
    private static final Logger logger = LoggerFactory.getLogger(CubeValidator.class);

    public static boolean isValidCube(Point[] points) {
        logger.debug("Validating cube with {} points", points.length);
        if (points.length != 8) return false;

        Set<Double> distances = new HashSet<>();
        Map<Double, Integer> freq = new HashMap<>();

        // перебираем все расстояния между парами точек
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double dist = distance(points[i], points[j]);
                dist = Math.round(dist * 1e6) / 1e6; // округляем
                distances.add(dist);
                freq.put(dist, freq.getOrDefault(dist, 0) + 1);
            }
        }

        // для куба должно быть ровно 3 разных расстояния:
        // 12 рёбер, 12 диагоналей граней, 4 главные диагонали
        if (distances.size() != 3) return false;

        List<Integer> counts = new ArrayList<>(freq.values());
        Collections.sort(counts); // [4, 12, 12]

        return counts.equals(List.of(4, 12, 12));
    }

    private static double distance(Point a, Point b) {
        double dx = a.getX() - b.getX();
        double dy = a.getY() - b.getY();
        double dz = a.getZ() - b.getZ();
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
}
