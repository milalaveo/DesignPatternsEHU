package com.milalaveo.shapeforge.domain.service;

import com.milalaveo.shapeforge.domain.model.Point;
import com.milalaveo.shapeforge.domain.model.impl.Cube;
import com.milalaveo.shapeforge.domain.service.CubeService;
import com.milalaveo.shapeforge.domain.service.impl.CubeServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CubeServiceTest {

    private final CubeService service = new CubeServiceImpl();

    private Cube buildUnitCube() {
        return new Cube(42L, new Point[]{
                new Point(0, 0, 0),
                new Point(0, 0, 1),
                new Point(0, 1, 0),
                new Point(0, 1, 1),
                new Point(1, 0, 0),
                new Point(1, 0, 1),
                new Point(1, 1, 0),
                new Point(1, 1, 1)
        });
    }

    @Test
    void testVolumeOfUnitCube() {
        Cube cube = buildUnitCube();
        double volume = service.calculateVolume(cube);
        assertEquals(1.0, volume, 0.0001, "Volume should be 1.0");
    }

    @Test
    void testSurfaceAreaOfUnitCube() {
        Cube cube = buildUnitCube();
        double surface = service.calculateSurfaceArea(cube);
        assertEquals(6.0, surface, 0.0001, "Surface area should be 6.0");
    }
}

