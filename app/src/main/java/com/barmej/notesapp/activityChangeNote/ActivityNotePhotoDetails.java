package com.barmej.notesapp.activityChangeNote;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.barmej.notesapp.Constants;
import com.barmej.notesapp.R;

public class ActivityNotePhotoDetails extends AppCompatActivity {
    RelativeLayout relativeLayout;
    ImageView imageView;
    EditText editText;

    ColorStateList colorStateList;
    Uri photoUri;
    String noteText;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_photo_details);

        relativeLayout = findViewById(R.id.relativeLayout_in_activity);
        imageView = findViewById(R.id.photoImageView_in_activity);
        editText = findViewById(R.id.photoNoteEditText);

        colorStateList = getIntent().getParcelableExtra(Constants.EXTRA_BACKGROUND_TO_ACTIVITY);
        noteText = getIntent().getStringExtra(Constants.EXTRA_NOTE_TEXT_TO_ACTIVITY);
        photoUri =  getIntent().getParcelableExtra(Constants.EXTRA_PHOTO_URI_TO_ACTIVITY);

        relativeLayout.setBackgroundTintList(colorStateList);
        imageView.setImageURI(photoUri);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               saveIntentPhotoUri();
            }
        });
        editText.setText(noteText);



    }

    @Override
    public void onBackPressed() {
        returnSavaData();
        super.onBackPressed();
    }

    private void returnSavaData(){
        Intent intent = new Intent();
        intent.putExtra(Constants.RETURN_TEXT_TO_MAIN, editText.getText().toString());
        intent.putExtra(Constants.RETURN_BACKGROUND_TO_MAIN, colorStateList);
        intent.putExtra(Constants.RETURN_PHOTO_URI_TO_MAIN, photoUri);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void saveIntentPhotoUri(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_photo)), Constants.PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.PICK_IMAGE){
                if (data != null && data.getData() != null) {
                    setSelectedPhotoUri(data.getData());
                    //getContentResolver().takePersistableUriPermission(data.getData(), Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
        }

    }

    private void setSelectedPhotoUri(Uri data){
        imageView.setImageURI(data);
        photoUri = data;
    }
}
