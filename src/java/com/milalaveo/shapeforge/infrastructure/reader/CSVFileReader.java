package com.milalaveo.shapeforge.infrastructure.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class CSVFileReader implements DataReader {
    private static final Logger logger = LoggerFactory.getLogger(CSVFileReader.class);

    @Override
    public List<String> readData(String fileName) {
        List<String> lines = new ArrayList<>();

        try (
                InputStream input = CSVFileReader.class.getClassLoader().getResourceAsStream(fileName);
                BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(input)))
        ) {
            String line;

            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

        } catch (NullPointerException e) {
            logger.error("Failed to read resource file: " + e.getMessage());
        } catch (IOException e) {
            logger.error("Failed to read resource file: " + e.getMessage());
        }

        logger.info("Parsed {} cubes from file", lines.size());
        return lines;
    }

}
