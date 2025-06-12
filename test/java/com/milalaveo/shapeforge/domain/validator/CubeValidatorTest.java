package com.milalaveo.shapeforge.domain.validator;

import com.milalaveo.shapeforge.domain.model.Point;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CubeValidatorTest {

    @Test
    void testValidCube() {
        Point[] validPoints = new Point[] {
                new Point(0, 0, 0),
                new Point(0, 0, 1),
                new Point(0, 1, 0),
                new Point(0, 1, 1),
                new Point(1, 0, 0),
                new Point(1, 0, 1),
                new Point(1, 1, 0),
                new Point(1, 1, 1)
        };

        boolean result = CubeValidator.isValidCube(validPoints);
        assertTrue(result, "Cube should be valid");
    }

    @Test
    void testInvalidCube() {
        Point[] invalidPoints = new Point[] {
                new Point(0, 0, 0),
                new Point(0, 0, 1),
                new Point(0, 1, 0),
                new Point(0, 1, 1),
                new Point(1, 0, 0),
                new Point(1, 0, 1),
                new Point(1, 1, 0),
                new Point(2, 2, 2)
        };

        boolean result = CubeValidator.isValidCube(invalidPoints);
        assertFalse(result, "Cube should be invalid");
    }
}
