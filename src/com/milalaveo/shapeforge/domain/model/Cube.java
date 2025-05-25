package com.milalaveo.shapeforge.domain.model;

import com.milalaveo.shapeforge.domain.event.Observable;
import com.milalaveo.shapeforge.domain.event.Observer;

import java.util.ArrayList;
import java.util.List;

public class Cube implements Observable {
    private final long id;
    private Point[] points;
    private final List<Observer> observers = new ArrayList<>();

    public Cube(long id, Point[] points) {
        this.id = id;
        this.points = points;
    }

    public long getId() { return id; }

    public Point[] getPoints() { return points; }

    public void setPoints(Point[] points) {
        this.points = points;
        notifyObservers();
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(this);
        }
    }

    @Override
    public String toString() {
        return "Cube #" + id;
    }
}
