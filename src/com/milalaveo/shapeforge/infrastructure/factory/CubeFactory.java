package com.milalaveo.shapeforge.infrastructure.factory;

import com.milalaveo.shapeforge.core.exception.InvalidCubeException;
import com.milalaveo.shapeforge.core.util.ParseUtils;
import com.milalaveo.shapeforge.domain.model.Cube;
import com.milalaveo.shapeforge.domain.model.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class  CubeFactory {

    private static long idCounter = 1;
    private static final Logger logger = LoggerFactory.getLogger(CubeFactory.class);

    public static Cube createFromLine(String line) throws InvalidCubeException {
        logger.info("Parsing cube from line: {}", line);
        String[] pointStrings = line.trim().split(";");
        if (pointStrings.length != 8) {
            throw new InvalidCubeException("A cube must have exactly 8 points.");
        }

        Point[] points = new Point[8];
        try {
            for (int i = 0; i < 8; i++) {
                points[i] = ParseUtils.parsePoint(pointStrings[i]);
            }
        } catch (NumberFormatException e) {
            logger.error("Failed to parse cube: {}", line, e);
            throw new InvalidCubeException("Failed to parse coordinates: " + e.getMessage());
        }
        long newId = idCounter++;
        logger.info("Created Cube {} with points {}", newId, points);

        return new Cube(newId, points);
    }
}
