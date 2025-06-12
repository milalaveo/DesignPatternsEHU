package com.milalaveo.shapeforge.main;

import com.milalaveo.shapeforge.domain.model.impl.Cube;
import com.milalaveo.shapeforge.domain.service.CubeService;
import com.milalaveo.shapeforge.domain.service.impl.CubeServiceImpl;
import com.milalaveo.shapeforge.infrastructure.factory.CubeFactory;
import com.milalaveo.shapeforge.infrastructure.reader.CSVFileReader;
import com.milalaveo.shapeforge.infrastructure.reader.DataReader;
import com.milalaveo.shapeforge.infrastructure.repository.CubeRepository;
import com.milalaveo.shapeforge.infrastructure.warehouse.Warehouse;
import com.milalaveo.shapeforge.specification.impl.BySurfaceAreaSpecification;
import com.milalaveo.shapeforge.specification.impl.ByVolumeSpecification;
import com.milalaveo.shapeforge.specification.Specification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private static String FILE_NAME = "cubes.txt";

    public static void main(String[] args) throws Exception {
        CubeRepository repository = new CubeRepository();
        CubeFactory cubeFactory = new CubeFactory();
        CubeService cubeService = new CubeServiceImpl();
        Warehouse warehouse = Warehouse.getInstance();

        logger.info("Start collecting cubes from file {}", FILE_NAME);

        DataReader reader = new CSVFileReader();
        List<String> lines = reader.readData(FILE_NAME);

        for (String line : lines) {
            Cube cube = cubeFactory.createFromLine(line);
            repository.save(cube);
            warehouse.put(cube);
        }

        logger.info("Cubes in repository: " + repository.size());

        repository.findAll().forEach(c -> printCubeInfo(c, cubeService));

        Specification<Cube> spec = new ByVolumeSpecification(0.9)
                .and(new BySurfaceAreaSpecification(5.9));

        logger.info("Cubes with volume >= 0.9 AND surface >= 5.9:");
        repository.findAll().stream()
                .filter(spec::isSatisfiedBy)
                .forEach(c -> logger.info("   ✅ " + c));

        printSortedCubes(repository, cubeService);
    }

    private static void printCubeInfo(Cube cube, CubeService cubeService) {
        logger.info("Cube created: " + cube);
        logger.info("Volume: " + cubeService.calculateVolume(cube));
        logger.info("Surface: " + cubeService.calculateSurfaceArea(cube));

        var data = Warehouse.getInstance().get(cube.getId());
        logger.info("Volume (from cache): " + data.volume());
        logger.info("Surface (from cache): " + data.surfaceArea());
    }

    private static void printSortedCubes(CubeRepository repository, CubeService cubeService) {
        logger.info("Cubes sorted by ID:");
        repository.sortById().forEach(c -> logger.info("ID: " + c.getId()));

        logger.info("Cubes sorted by Volume:");
        repository.sortByVolume().forEach(c -> {
            double volume = cubeService.calculateVolume(c);
            logger.info("ID: {} → Volume: {}", c.getId(), volume);
        });

        logger.info("Cubes sorted by Surface Area:");
        repository.sortBySurfaceArea().forEach(c -> {
            double surface = cubeService.calculateSurfaceArea(c);
            logger.info("ID: {} → Surface Area: {}", c.getId(), surface);
        });
    }

}
