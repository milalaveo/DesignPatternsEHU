package com.milalaveo.shapeforge.infrastructure.repository;

import com.milalaveo.shapeforge.domain.model.impl.Cube;
import com.milalaveo.shapeforge.domain.service.CubeService;
import com.milalaveo.shapeforge.domain.service.impl.CubeServiceImpl;
import com.milalaveo.shapeforge.specification.Specification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class CubeRepository {
    private static final Logger logger = LoggerFactory.getLogger(CubeRepository.class);

    private final List<Cube> cubes = new ArrayList<>();

    private final CubeService cubeService;

    public CubeRepository() {
        this.cubeService = new CubeServiceImpl();
    }

    public void save(Cube cube) {
        cubes.removeIf(existing -> existing.getId() == cube.getId());
        cubes.add(cube);
        logger.info("Saved cube #{}", cube.getId());
    }

    public Optional<Cube> findById(long id) {
        return cubes.stream()
                .filter(cube -> cube.getId() == id)
                .findFirst();
    }

    public List<Cube> findAll() {
        return new ArrayList<>(cubes);
    }

    public boolean delete(long id) {
        boolean removed = cubes.removeIf(cube -> cube.getId() == id);
        if (removed) {
            logger.info("Deleted cube #{}", id);
        }
        return removed;
    }

    public void clear() {
        cubes.clear();
    }

    public int size() {
        return cubes.size();
    }

    public List<Cube> query(Specification<Cube> spec) {
        logger.debug("Querying cubes by specification...");
        return cubes.stream()
                .filter(spec::isSatisfiedBy)
                .collect(Collectors.toList());
    }

    public List<Cube> sort(Comparator<Cube> comparator) {
        return cubes.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public List<Cube> sortByVolume() {
        return sort(Comparator.comparingDouble(cubeService::calculateVolume));
    }

    public List<Cube> sortBySurfaceArea() {
        return sort(Comparator.comparingDouble(cubeService::calculateSurfaceArea));
    }

    public List<Cube> sortById() {
        return sort(Comparator.comparingLong(Cube::getId));
    }
}
