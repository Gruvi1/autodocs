package ru.nsu.astakhov.autodocs.ui;

public interface Observable {
    void addListener(Listener l);
    void removeListener(Listener l);
    void notifyAllTableUpdate(String updateStatus);
}
