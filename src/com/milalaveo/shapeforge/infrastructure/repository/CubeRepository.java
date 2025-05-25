package com.milalaveo.shapeforge.infrastructure.repository;

import com.milalaveo.shapeforge.domain.model.Cube;
import com.milalaveo.shapeforge.domain.service.CubeService;
import com.milalaveo.shapeforge.specification.Specification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class CubeRepository {
    private static final Logger logger = LoggerFactory.getLogger(CubeRepository.class);

    private final Map<Long, Cube> cubes = new HashMap<>();

    public void save(Cube cube) {
        cubes.put(cube.getId(), cube);
        logger.info("Saved cube #{}", cube.getId());
    }

    public Optional<Cube> findById(long id) {
        return Optional.ofNullable(cubes.get(id));
    }

    public List<Cube> findAll() {
        return new ArrayList<>(cubes.values());
    }

    public boolean delete(long id) {
        logger.info("Deleted cube #{}", id);
        return cubes.remove(id) != null;
    }

    public void clear() {
        cubes.clear();
    }

    public int size() {
        return cubes.size();
    }

    public List<Cube> query(Specification<Cube> spec) {
        logger.debug("Querying cubes by specification...");
        return cubes.values().stream()
                .filter(spec::isSatisfiedBy)
                .collect(Collectors.toList());
    }

    public List<Cube> sortByVolume() {
        return cubes.values().stream()
                .sorted(Comparator.comparingDouble(CubeService::calculateVolume))
                .collect(Collectors.toList());
    }

    public List<Cube> sortBySurfaceArea() {
        return cubes.values().stream()
                .sorted(Comparator.comparingDouble(CubeService::calculateSurfaceArea))
                .collect(Collectors.toList());
    }

    public List<Cube> sortById() {
        return cubes.values().stream()
                .sorted(Comparator.comparingLong(Cube::getId))
                .collect(Collectors.toList());
    }

}
