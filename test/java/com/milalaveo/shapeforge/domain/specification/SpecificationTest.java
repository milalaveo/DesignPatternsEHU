package com.milalaveo.shapeforge.domain.specification;

import com.milalaveo.shapeforge.domain.model.impl.Cube;
import com.milalaveo.shapeforge.domain.model.Point;
import com.milalaveo.shapeforge.infrastructure.repository.CubeRepository;
import com.milalaveo.shapeforge.infrastructure.warehouse.Warehouse;
import com.milalaveo.shapeforge.specification.Specification;
import com.milalaveo.shapeforge.specification.impl.ByEdgeLengthSpecification;
import com.milalaveo.shapeforge.specification.impl.ByIdSpecification;
import com.milalaveo.shapeforge.specification.impl.ByPositionSpecification;
import com.milalaveo.shapeforge.specification.impl.ByVolumeSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SpecificationTest {

    private CubeRepository repo;

    @BeforeEach
    void setup() {
        repo = new CubeRepository();
        Warehouse.getInstance().clear();

        Cube c1 = new Cube(1L, unitCube());      // 1.0
        Cube c2 = new Cube(2L, scaledCube(2));   // 8.0
        Cube c3 = new Cube(3L, scaledCube(3));   // 27.0

        repo.save(c1);
        repo.save(c2);
        repo.save(c3);

        Warehouse.getInstance().put(c1);
        Warehouse.getInstance().put(c2);
        Warehouse.getInstance().put(c3);
    }

    private Point[] unitCube() {
        return new Point[] {
                new Point(0, 0, 0), new Point(0, 0, 1),
                new Point(0, 1, 0), new Point(0, 1, 1),
                new Point(1, 0, 0), new Point(1, 0, 1),
                new Point(1, 1, 0), new Point(1, 1, 1)
        };
    }

    private Point[] scaledCube(int scale) {
        return new Point[] {
                new Point(0, 0, 0), new Point(0, 0, scale),
                new Point(0, scale, 0), new Point(0, scale, scale),
                new Point(scale, 0, 0), new Point(scale, 0, scale),
                new Point(scale, scale, 0), new Point(scale, scale, scale)
        };
    }

    @Test
    void testVolumeSpecification() {
        Specification<Cube> spec = new ByVolumeSpecification(5.0);
        List<Cube> results = repo.query(spec);

        assertEquals(2, results.size(), "Should return 2 cubes with volume > 5.0");
    }

    @Test
    void testCombinedSpecification() {
        Specification<Cube> gt5 = new ByVolumeSpecification(5.0);
        Specification<Cube> gt20 = new ByVolumeSpecification(20.0);
        Specification<Cube> lte20 = gt20.not();

        Specification<Cube> between = gt5.and(lte20); // volume > 5 AND volume <= 20

        List<Cube> results = repo.query(between);
        assertEquals(1, results.size(), "Should return 1 cube with volume > 5 and <= 20");
    }

    @Test
    void testByIdSpecification() {
        Specification<Cube> spec = new ByIdSpecification(2L);
        List<Cube> results = repo.query(spec);

        assertEquals(1, results.size(), "Should return only cube with ID 2");
        assertEquals(2L, results.get(0).getId());
    }

    @Test
    void testByEdgeLengthSpecification() {
        Specification<Cube> spec = new ByEdgeLengthSpecification(1.5);
        List<Cube> results = repo.query(spec);

        assertEquals(2, results.size(), "Should return 2 cubes with edge >= 1.5 (scales 2 and 3)");
    }

    @Test
    void testByPositionSpecification() {
        Specification<Cube> spec = new ByPositionSpecification(2.5); // Z > 2.5
        List<Cube> results = repo.query(spec);

        assertEquals(1, results.size(), "Only the biggest cube (scale 3) should have Z > 2.5");
        assertEquals(3L, results.get(0).getId());
    }

}
