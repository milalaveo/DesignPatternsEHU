package com.milalaveo.shapeforge.infrastructure.warehouse;

import com.milalaveo.shapeforge.domain.event.Observer;
import com.milalaveo.shapeforge.domain.model.Cube;
import com.milalaveo.shapeforge.domain.service.CubeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Warehouse implements Observer {
    private static final Logger logger = LoggerFactory.getLogger(Warehouse.class);

    private static final Warehouse INSTANCE = new Warehouse();

    private final Map<Long, CubeData> storage = new HashMap<>();

    private Warehouse() {}

    public static Warehouse getInstance() {
        return INSTANCE;
    }

    public void put(Cube cube) {
        double volume = CubeService.calculateVolume(cube);
        double surface = CubeService.calculateSurfaceArea(cube);
        storage.put(cube.getId(), new CubeData(volume, surface));
        cube.attach(this); // sub on updates
        logger.info("Added cube #{} to Warehouse", cube.getId());
    }

    public CubeData get(long cubeId) {
        logger.debug("Fetching data for cube #{}", cubeId);
        return storage.get(cubeId);
    }

    @Override
    public void update(Cube cube) {
        logger.info("Cube #{} updated, recalculating...", cube.getId());
        put(cube); // recount
    }

    public void clear() {
        logger.warn("Warehouse cleared");
        storage.clear();
    }

    public record CubeData(double volume, double surfaceArea) {}
}
