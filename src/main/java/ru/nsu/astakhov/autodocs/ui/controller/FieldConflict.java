package ru.nsu.astakhov.autodocs.ui.controller;

import lombok.Getter;
import ru.nsu.astakhov.autodocs.model.StudentEntity;
import ru.nsu.astakhov.autodocs.ui.view.dialog.FieldConflictDialog;

import java.awt.*;
import java.util.function.Consumer;

@Getter
public class FieldConflict extends Conflict {
    private final String studentName;
    private final String fieldName;
    private final String practiceValue;
    private final String thesisValue;

    public FieldConflict(Consumer<String> resolver, StudentEntity entity, String fieldName, String practiceValue, String thesisValue) {
        super(entity, resolver);
        this.studentName = entity.getFullName();
        this.fieldName = fieldName;
        this.practiceValue = practiceValue;
        this.thesisValue = thesisValue;
    }

    @Override
    protected String getResolutionDialogResult(Frame owner) {
        return new FieldConflictDialog(owner, this).showDialog();
    }
}
