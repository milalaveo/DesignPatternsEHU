package com.milalaveo.shapeforge.infrastructure.factory;

import com.milalaveo.shapeforge.core.exception.InvalidCubeException;
import com.milalaveo.shapeforge.core.util.ParseUtils;
import com.milalaveo.shapeforge.domain.model.impl.Cube;
import com.milalaveo.shapeforge.domain.model.Point;
import com.milalaveo.shapeforge.infrastructure.util.IDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CubeFactory {

    private static final Integer POINT_COUNT = 8;

    private static final Logger logger = LoggerFactory.getLogger(CubeFactory.class);
    private static ParseUtils parseUtils = new ParseUtils();

    public CubeFactory() {
        parseUtils = new ParseUtils();
    }

    public Cube createFromLine(String line) throws InvalidCubeException {
        logger.info("Parsing cube from line: {}", line);
        String[] pointStrings = line.trim().split(";");

        if (pointStrings.length != POINT_COUNT) {
            throw new InvalidCubeException("A cube must have exactly " + POINT_COUNT + " points.");
        }

        Point[] points = new Point[POINT_COUNT];
        try {
            for (int i = 0; i < POINT_COUNT; i++) {
                points[i] = parseUtils.parsePoint(pointStrings[i]);
            }
        } catch (NumberFormatException e) {
            logger.error("Failed to parse cube: {}", line, e);
            throw new InvalidCubeException("Failed to parse coordinates: " + e.getMessage());
        }
        long newId = IDGenerator.getNextId();

        logger.info("Created Cube {} with points {}", newId, points);

        return new Cube(newId, points);
    }
}
