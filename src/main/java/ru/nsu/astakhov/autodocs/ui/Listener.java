package ru.nsu.astakhov.autodocs.ui;

public interface Listener {
    void onTableUpdate(String updateStatus);
    void onDocumentGeneration(String generateStatus);
}
