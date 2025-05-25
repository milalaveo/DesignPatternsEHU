package com.milalaveo.shapeforge.main;

import com.milalaveo.shapeforge.domain.model.Cube;
import com.milalaveo.shapeforge.domain.service.CubeService;
import com.milalaveo.shapeforge.infrastructure.factory.CubeFactory;
import com.milalaveo.shapeforge.infrastructure.reader.TxtCubeReader;
import com.milalaveo.shapeforge.infrastructure.repository.CubeRepository;
import com.milalaveo.shapeforge.infrastructure.warehouse.Warehouse;
import com.milalaveo.shapeforge.specification.impl.BySurfaceAreaSpecification;
import com.milalaveo.shapeforge.specification.impl.ByVolumeSpecification;
import com.milalaveo.shapeforge.specification.Specification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.List;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(TxtCubeReader.class);
    public static void main(String[] args) throws Exception {
        CubeRepository repository = new CubeRepository();
        Cube sampleCube = CubeFactory.createFromLine("0 0 0; 0 0 1; 0 1 0; 0 1 1; 1 0 0; 1 0 1; 1 1 0; 1 1 1");
        Warehouse.getInstance().put(sampleCube);
        printCubeInfo(sampleCube);

        List<Cube> cubes = loadCubesFromFile("cubes.txt");
        cubes.forEach(repository::save);

        logger.info("Cubes in repository: " + repository.size());
        repository.findAll().forEach(c -> logger.info("🔹 " + c));

        filterCubes(repository);

        printSortedCubes(repository);
    }

    private static void printCubeInfo(Cube cube) {
        logger.info("Cube created: " + cube);
        logger.info("Volume: " + CubeService.calculateVolume(cube));
        logger.info("Surface: " + CubeService.calculateSurfaceArea(cube));

        var data = Warehouse.getInstance().get(cube.getId());
        logger.info("Volume (from cache): " + data.volume());
        logger.info("Surface (from cache): " + data.surfaceArea());
    }

    private static List<Cube> loadCubesFromFile(String fileName) {
        Path path = Path.of(fileName);
        List<Cube> cubes = TxtCubeReader.readFromFile(path.toString());
        logger.info("Valid cubes loaded: " + cubes.size());
        cubes.forEach(c -> logger.info("   - " + c));
        return cubes;
    }

    private static void filterCubes(CubeRepository repo) {
        Specification<Cube> spec = new ByVolumeSpecification(0.9)
                .and(new BySurfaceAreaSpecification(5.9));

        logger.info("Cubes with volume >= 0.9 AND surface >= 5.9:");
        repo.findAll().stream()
                .filter(spec::isSatisfiedBy)
                .forEach(c -> logger.info("   ✅ " + c));
    }

    private static void printSortedCubes(CubeRepository repository) {
        logger.info("Cubes sorted by ID:");
        repository.sortById().forEach(c -> logger.info("ID: " + c.getId()));

        logger.info("Cubes sorted by Volume:");
        repository.sortByVolume().forEach(c -> {
            double volume = CubeService.calculateVolume(c);
            logger.info("ID: {} → Volume: {}", c.getId(), volume);
        });

        logger.info("Cubes sorted by Surface Area:");
        repository.sortBySurfaceArea().forEach(c -> {
            double surface = CubeService.calculateSurfaceArea(c);
            logger.info("ID: {} → Surface Area: {}", c.getId(), surface);
        });
    }

}
