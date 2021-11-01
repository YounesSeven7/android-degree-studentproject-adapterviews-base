package com.barmej.notesapp.dataItem;

import android.content.res.ColorStateList;
import android.net.Uri;
import android.widget.ImageView;

public class PhotoNoteItem extends NoteItem{
    Uri photoUri;

    public PhotoNoteItem(String noteText, ColorStateList colorStateList, Uri photoUri) {
        super(noteText, colorStateList);
        this.photoUri = photoUri;
    }

    public Uri getPhotoUri() {
        return photoUri;
    }
}
