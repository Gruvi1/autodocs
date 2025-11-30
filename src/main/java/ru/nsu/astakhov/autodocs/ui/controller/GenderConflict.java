package ru.nsu.astakhov.autodocs.ui.controller;

import lombok.Getter;
import ru.nsu.astakhov.autodocs.model.StudentEntity;
import ru.nsu.astakhov.autodocs.ui.view.dialog.GenderConflictDialog;

import java.awt.*;

@Getter
public class GenderConflict extends Conflict {
    private final String studentName;

    public GenderConflict(StudentEntity entity) {
        super(entity, entity::setGender);
        this.studentName = entity.getFullName();
    }

    @Override
    protected String getResolutionDialogResult(Frame owner) {
        return new GenderConflictDialog(owner, this).showDialog();
    }
}
