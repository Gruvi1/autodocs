package ru.nsu.astakhov.autodocs.ui.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.nsu.astakhov.autodocs.model.StudentEntity;

import java.awt.*;
import java.util.function.Consumer;

@RequiredArgsConstructor
// TODO: вынести в другой пакет
public abstract class Conflict {
    @Getter
    private final StudentEntity entity;
    private final Consumer<String> resolver;

    protected abstract String getResolutionDialogResult(Frame owner);

    public final void resolveViaDialog(Frame owner) {
        String answer = getResolutionDialogResult(owner);
        resolver.accept(answer);
    }
}
