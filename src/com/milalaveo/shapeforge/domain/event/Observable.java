package com.milalaveo.shapeforge.domain.event;

public interface Observable {
    void attach(Observer observer);
    void detach(Observer observer);
    void notifyObservers();
}
