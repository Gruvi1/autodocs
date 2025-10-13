package ru.nsu.astakhov.autodocs.core.view;

import javax.swing.*;

public abstract class Screen {

    abstract void setupKeyBindings(JPanel panel);
    abstract JPanel create();
}
