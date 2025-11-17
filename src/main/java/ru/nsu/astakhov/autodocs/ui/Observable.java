package ru.nsu.astakhov.autodocs.ui;

public interface Observable {
    void addListener(Listener l);
    void notifyAllTableUpdate(String updateStatus);
    void notifyAllDocumentGeneration(String generateStatus);
}
