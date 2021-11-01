package com.barmej.notesapp.activityChangeNote;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.barmej.notesapp.Constants;
import com.barmej.notesapp.R;

public class ActivityNoteDetails extends AppCompatActivity {
    ConstraintLayout constraintLayout;
    EditText editText;

    ColorStateList colorStateList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        constraintLayout = findViewById(R.id.constraint_activity_note);
        editText = findViewById(R.id.noteEditText_in_activity_change);

        String noteText = getIntent().getStringExtra(Constants.EXTRA_NOTE_TEXT_TO_ACTIVITY);
        colorStateList = getIntent().getParcelableExtra(Constants.EXTRA_BACKGROUND_TO_ACTIVITY);


        constraintLayout.setBackgroundTintList(colorStateList);
        editText.setText(noteText);





    }

    @Override
    public void onBackPressed() {
        returnSaveData();
        super.onBackPressed();
    }

    private void returnSaveData() {
        Intent intent = new Intent();
        intent.putExtra(Constants.RETURN_TEXT_TO_MAIN , editText.getText().toString());
        intent.putExtra(Constants.RETURN_BACKGROUND_TO_MAIN, colorStateList);
        setResult(RESULT_OK, intent);
        finish();
    }
}
