# 🧊 ShapeForge

**ShapeForge** is a Java-based solution for the task for processing and analyzing 3D geometric figures using object-oriented principles and design patterns.

---

## 📌 Project Overview

The application reads coordinates of 3D points from a file, identifies whether the input defines a valid cube, calculates geometric properties (volume, surface area), validates the data, stores valid cubes in a repository, and automatically updates cached values when a cube is modified.

This project demonstrates the use of several classic design patterns:

- **Factory Method** — for creating cube objects from text input
- **Observer** — for notifying the `Warehouse` of cube updates
- **Singleton** — ensures a single instance of the `Warehouse`
- **Repository** — central storage for all created cubes
- **Object Pool** — caching of calculated properties (volume, area)
- **State** — to determine if the figure is a valid cube
- **Specification** — flexible filtering of figures based on criteria

---

## 📁 Project Architecture

```text
src/
└── com/milalaveo/shapeforge/
    ├── main/               # Entry point of the application
    ├── core/
    │   ├── exception/      # Custom exception classes
    │   └── util/           # Utility helpers (math, parsing, etc.)
    ├── domain/
    │   ├── model/          # Domain models: Point, Cube
    │   ├── service/        # Geometry logic: area, volume, type
    │   ├── validator/      # Validation of figure properties
    │   └── event/          # Observer interfaces
    ├── infrastructure/
    │   ├── factory/        # CubeFactory (Factory Method)
    │   ├── reader/         # TxtCubeReader (file input)
    │   ├── repository/     # CubeRepository (Repository Pattern)
    │   └── warehouse/      # Warehouse (Object Pool + Singleton)
    ├── specification/      # Query filters (e.g. by volume)
    └── test/               # Unit tests
