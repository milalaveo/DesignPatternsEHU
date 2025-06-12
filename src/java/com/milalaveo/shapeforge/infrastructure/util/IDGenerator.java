package com.milalaveo.shapeforge.infrastructure.util;

import java.util.concurrent.atomic.AtomicLong;

public class IDGenerator {
    private static final AtomicLong idCounter = new AtomicLong(1);

    public static long getNextId() {
        return idCounter.getAndIncrement();
    }
}