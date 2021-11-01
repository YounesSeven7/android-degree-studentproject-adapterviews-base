package com.barmej.notesapp.dataItem;

import android.content.res.ColorStateList;
import android.widget.CheckBox;

public class CheckNoteItem extends NoteItem{
    int isChecked;

    public CheckNoteItem(String noteText, ColorStateList colorStateList, int isChecked) {
        super(noteText, colorStateList);
        this.isChecked = isChecked;
    }

    public int getIsChecked() {
        return isChecked;
    }
}
