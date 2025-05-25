package com.milalaveo.shapeforge.infrastructure.reader;

import com.milalaveo.shapeforge.core.exception.InvalidCubeException;
import com.milalaveo.shapeforge.domain.model.Cube;
import com.milalaveo.shapeforge.domain.validator.CubeValidator;
import com.milalaveo.shapeforge.infrastructure.factory.CubeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class TxtCubeReader {
    private static final Logger logger = LoggerFactory.getLogger(TxtCubeReader.class);

    public static List<Cube> readFromFile(String fileName) {
        List<Cube> cubes = new ArrayList<>();

        try (
                InputStream input = TxtCubeReader.class.getClassLoader().getResourceAsStream(fileName);
                BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(input)))
        ) {
            String line;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                try {
                    Cube cube = CubeFactory.createFromLine(line);
                    if (CubeValidator.isValidCube(cube.getPoints())) {
                        cubes.add(cube);
                    } else {
                        logger.warn("Invalid cube at line " + lineNumber);
                    }
                } catch (InvalidCubeException e) {
                    logger.error("Parse error at line " + lineNumber + ": " + e.getMessage());
                }

                lineNumber++;
            }

        } catch (IOException | NullPointerException e) {
            logger.error("Failed to read resource file: " + e.getMessage());
        }

        logger.info("Parsed {} cubes from file", cubes.size());
        return cubes;
    }

}
