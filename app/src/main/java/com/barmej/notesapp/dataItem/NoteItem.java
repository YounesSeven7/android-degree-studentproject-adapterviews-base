package com.barmej.notesapp.dataItem;

import android.content.res.ColorStateList;
import android.net.Uri;
import android.view.View;

public class NoteItem {


    String noteText;
    ColorStateList colorStateList;




    public NoteItem(String noteText, ColorStateList colorStateList) {
        this.noteText = noteText;
        this.colorStateList = colorStateList;
    }

    public String getNoteText() {
        return noteText;
    }

    public ColorStateList getColorStateList() {
        return colorStateList;
    }

}
