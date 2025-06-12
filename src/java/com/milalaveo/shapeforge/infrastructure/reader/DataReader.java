package com.milalaveo.shapeforge.infrastructure.reader;

import com.milalaveo.shapeforge.core.exception.FileReadingException;

import java.util.List;

public interface DataReader {
    List<String> readData(String source) throws FileReadingException;
}
