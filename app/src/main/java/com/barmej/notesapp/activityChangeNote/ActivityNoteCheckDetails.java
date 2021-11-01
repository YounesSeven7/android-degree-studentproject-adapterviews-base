package com.barmej.notesapp.activityChangeNote;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.barmej.notesapp.Constants;
import com.barmej.notesapp.R;

public class ActivityNoteCheckDetails extends AppCompatActivity {
    ConstraintLayout constraintLayout;
    EditText editText;
    CheckBox checkBox;

    String noteText;
    ColorStateList colorStateList;
    int isChecked;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_check_details);

        constraintLayout = findViewById(R.id.constraint_activity);
        editText = findViewById(R.id.noteText_item_in_activity);
        checkBox = findViewById(R.id.checkNote_item_in_avtivity);

        noteText = getIntent().getStringExtra(Constants.EXTRA_NOTE_TEXT_TO_ACTIVITY);
        colorStateList = getIntent().getParcelableExtra(Constants.EXTRA_BACKGROUND_TO_ACTIVITY);
        isChecked = getIntent().getIntExtra(Constants.EXTRA_IS_CHECKED_TO_ACTIVITY, 3);



        editText.setText(noteText);
        constraintLayout.setBackgroundTintList(colorStateList);
        if (isChecked == 1)
            checkBox.setChecked(true);
         else if (isChecked == 0)
            checkBox.setChecked(false);
    }

    @Override
    public void onBackPressed() {
        returnSaveDate();
        super.onBackPressed();
    }

    private void returnSaveDate(){
        Intent intent = new Intent();
        intent.putExtra(Constants.RETURN_TEXT_TO_MAIN, editText.getText().toString());
        intent.putExtra(Constants.RETURN_BACKGROUND_TO_MAIN, colorStateList);
        intent.putExtra(Constants.RETURN_IS_CHECKED_TO_MAIN, isChecked);
        setResult(RESULT_OK, intent);
        finish();
    }

}
