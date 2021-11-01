package com.barmej.notesapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class ActivityAddNewNote extends AppCompatActivity {
    RadioButton radioButtonAddNoteDetails;
    RadioButton radioButtonAddCheckDetails;
    RadioButton radioButtonAddNotePhotoDetails;

    CardView cardViewNote, cardViewCheckNote, cardViewNotePhoto;

    EditText editText;
    CheckBox checkBox;
    ImageView imageView;

    String noteText;
    ColorStateList colorBackGroundTint;
    Uri photoUri;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_note);

        cardViewNote = findViewById(R.id.cardViewNote);
        cardViewCheckNote = findViewById(R.id.cardViewCheckNote);
        cardViewNotePhoto = findViewById(R.id.cardViewPhoto);

        typeRadioGroup();
        colorRadioGroup();

        findViewById(R.id.button_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addButtonOnClicked();
            }
        });

    }

    private void typeRadioGroup(){
        findViewById(R.id.radioButton6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNoteDetails();
            }
        });

        findViewById(R.id.radioButton5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNoteCheck();
            }
        });

        findViewById(R.id.radioButton4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNotePhoto();
                imageView = findViewById(R.id.photoImageView_in_activity);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if ((ActivityCompat.checkSelfPermission(ActivityAddNewNote.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)){

                            ActivityCompat.requestPermissions(ActivityAddNewNote.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constants.READ_PHOTO_FROM_GALLERY_PERMISSION);
                        } else {
                            addPhoto();
                        }
                    }
                });
            }
        });
    }

    private void addNoteDetails(){
        cardViewCheckNote.setVisibility(View.GONE);
        cardViewNotePhoto.setVisibility(View.GONE);
        cardViewNote.setVisibility(View.VISIBLE);
    }

    private void addNoteCheck(){
        cardViewNote.setVisibility(View.GONE);
        cardViewNotePhoto.setVisibility(View.GONE);
        cardViewCheckNote.setVisibility(View.VISIBLE);
    }

    private void addNotePhoto(){
        cardViewNote.setVisibility(View.GONE);
        cardViewCheckNote.setVisibility(View.GONE);
        cardViewNotePhoto.setVisibility(View.VISIBLE);
    }

    private void colorRadioGroup(){
        findViewById(R.id.radioButton3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBlueColor();
            }
        });

        findViewById(R.id.radioButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setYellowColor();
            }
        });

        findViewById(R.id.radioButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRedColor();
            }
        });
    }

    private void setBlueColor(){
        colorBackGroundTint = ContextCompat.getColorStateList(this,R.color.blue);
        cardViewNote.setBackgroundTintList(colorBackGroundTint);
        cardViewCheckNote.setBackgroundTintList(colorBackGroundTint);
        cardViewNotePhoto.setBackgroundTintList(colorBackGroundTint);

    }

    private void setYellowColor(){
        colorBackGroundTint = ContextCompat.getColorStateList(this,R.color.yellow);
        cardViewNote.setBackgroundTintList(colorBackGroundTint);
        cardViewCheckNote.setBackgroundTintList(colorBackGroundTint);
        cardViewNotePhoto.setBackgroundTintList(colorBackGroundTint);
    }

    private void setRedColor(){
        colorBackGroundTint = ContextCompat.getColorStateList(this,R.color.red);
        cardViewNote.setBackgroundTintList(colorBackGroundTint);
        cardViewCheckNote.setBackgroundTintList(colorBackGroundTint);
        cardViewNotePhoto.setBackgroundTintList(colorBackGroundTint);

    }

    private void addButtonOnClicked(){
        Intent intent = new Intent();
        if (cardViewNote.getVisibility() == View.VISIBLE && cardViewCheckNote.getVisibility() == View.GONE && cardViewNotePhoto.getVisibility() == View.GONE){
            editText = findViewById(R.id.noteEditText_in_activity_change);
            noteText = editText.getText().toString();
            intent.putExtra(Constants.ADD_NEW_NOTE_TEXT, noteText);
            intent.putExtra(Constants.ADD_NEW_NOTE_BACKGROUND, colorBackGroundTint);
            setResult(Constants.RESULT_CODE_NOTE_VIEW, intent);
        } else if (cardViewNote.getVisibility() == View.GONE && cardViewCheckNote.getVisibility() == View.VISIBLE && cardViewNotePhoto.getVisibility() == View.GONE){
            Toast.makeText(this, "i am check note", Toast.LENGTH_SHORT).show();
            editText = findViewById(R.id.noteText_item_in_activity);
            noteText = editText.getText().toString();

            intent.putExtra(Constants.ADD_NEW_NOTE_TEXT, noteText);
            intent.putExtra(Constants.ADD_NEW_NOTE_BACKGROUND, colorBackGroundTint);

            checkBox = findViewById(R.id.checkNote_item_in_avtivity);
            if (checkBox.isChecked()){
                intent.putExtra(Constants.IS_CHECKBOX_CHECKED, 1);
            } else {
                intent.putExtra(Constants.IS_CHECKBOX_CHECKED, 0);
            }

            setResult(Constants.RESULT_CODE_NOTE_CHECK_VIEW, intent);
        } else if (cardViewNote.getVisibility() == View.GONE && cardViewCheckNote.getVisibility() == View.GONE && cardViewNotePhoto.getVisibility() == View.VISIBLE){
            editText = findViewById(R.id.photoNoteEditText);
            noteText = editText.getText().toString();
            intent.putExtra(Constants.ADD_NEW_NOTE_TEXT, noteText);
            intent.putExtra(Constants.ADD_NEW_NOTE_BACKGROUND, colorBackGroundTint);
            intent.putExtra(Constants.ADD_NEW_NOTE_PHOTO, photoUri);
            setResult(Constants.RESULT_CODE_NOTE_PHOTO_VIEW, intent);
        }
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.READ_PHOTO_FROM_GALLERY_PERMISSION){
            addPhoto();
        } else {
            Toast.makeText(this, R.string.read_permission_needed_to_access_files, Toast.LENGTH_SHORT).show();
        }
    }

    private void addPhoto(){
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
            if (data != null && data.getData() != null){
                setSelectedPhotoUri(data.getData());
                getContentResolver().takePersistableUriPermission(data.getData(), Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } else {
                Toast.makeText(this, R.string.faul_toget_photo, Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void setSelectedPhotoUri(Uri data){
        imageView.setImageURI(data);
        photoUri = data;
    }
}
