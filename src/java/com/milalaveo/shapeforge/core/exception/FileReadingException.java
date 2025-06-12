package com.milalaveo.shapeforge.core.exception;

public class FileReadingException extends Exception {

    public FileReadingException(String filePath, String reason) {
        super(String.format("Error reading file '%s': %s", filePath, reason));
    }

    public FileReadingException(String filePath, String reason, Throwable cause) {
        super(String.format("Error reading file '%s': %s", filePath, reason), cause);
    }
}
