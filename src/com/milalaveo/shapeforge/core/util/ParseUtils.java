package com.milalaveo.shapeforge.core.util;

import com.milalaveo.shapeforge.domain.model.Point;

public class ParseUtils {

    public static Point parsePoint(String raw) throws NumberFormatException {
        String[] parts = raw.trim().split("\\s+");
        if (parts.length != 3) {
            throw new NumberFormatException("Point must have 3 coordinates: " + raw);
        }

        double x = Double.parseDouble(parts[0]);
        double y = Double.parseDouble(parts[1]);
        double z = Double.parseDouble(parts[2]);
        return new Point(x, y, z);
    }
}
