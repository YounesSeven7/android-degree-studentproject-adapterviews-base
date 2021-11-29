package com.barmej.notesapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.barmej.notesapp.activityChangeNote.ActivityNoteCheckDetails;
import com.barmej.notesapp.activityChangeNote.ActivityNoteDetails;
import com.barmej.notesapp.activityChangeNote.ActivityNotePhotoDetails;
import com.barmej.notesapp.adapter.AddNotesAdapter;
import com.barmej.notesapp.dataItem.CheckNoteItem;
import com.barmej.notesapp.dataItem.NoteItem;
import com.barmej.notesapp.dataItem.PhotoNoteItem;
import com.barmej.notesapp.interfase.OnClickItemListener;
import com.barmej.notesapp.interfase.OnLongClickItemListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton floatingActionButton;

    AddNotesAdapter addNotesAdapter;
    RecyclerView recyclerView;


    List<NoteItem> noteItemList;
    public int myPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        recyclerView = findViewById(R.id.recycler_view_photos);
        floatingActionButton = findViewById(R.id.floating_button_add);

        noteItemList = new ArrayList<>();
        addNotesAdapter = new AddNotesAdapter(noteItemList, new OnClickItemListener() {
            @Override
            public void onClickNoteItemListener(int position) {
                changeNoteItem(position);
            }

            @Override
            public void onClickCheckNoteItemListener(int position) {
                changeCheckNoteItem(position);
            }

            @Override
            public void onClickPhotoNoteItemListener(int position) {
                changePhotoNoteItem(position);
            }
        }
                , new OnLongClickItemListener() {
            @Override
            public void onLongClickItemListener(int position) {
                askForRemove(position);
            }

        });

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(addNotesAdapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewNote();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String noteText;
        if (data != null) {
            if (requestCode == Constants.ADE_NOTE){
                if (resultCode == Constants.RESULT_CODE_NOTE_VIEW){
                    //..........................................................................................jest note
                    noteText = data.getStringExtra(Constants.ADD_NEW_NOTE_TEXT);
                    if (!(noteText.isEmpty()) && !(noteText.equals(" "))){
                        ColorStateList colorStateList = data.getParcelableExtra(Constants.ADD_NEW_NOTE_BACKGROUND);
                        noteItemList.add(new NoteItem(noteText, colorStateList));
                        addNotesAdapter.notifyItemInserted(noteItemList.size() - 1);
                    } else
                        Toast.makeText(this, R.string.no_text, Toast.LENGTH_SHORT).show();
                } else if (resultCode == Constants.RESULT_CODE_NOTE_CHECK_VIEW) {
                    //..........................................................................................check note
                    noteText = data.getStringExtra(Constants.ADD_NEW_NOTE_TEXT);
                    if (!(noteText.isEmpty()) && !(noteText.equals(" "))) {
                        ColorStateList colorStateList = data.getParcelableExtra(Constants.ADD_NEW_NOTE_BACKGROUND);
                        int isChecked = data.getIntExtra(Constants.IS_CHECKBOX_CHECKED, 3);

                        CheckNoteItem checkNoteItem = new CheckNoteItem(noteText, colorStateList, isChecked);
                        noteItemList.add(checkNoteItem);
                        addNotesAdapter.notifyItemInserted(noteItemList.size() - 1);
                    }
                } else if (resultCode == Constants.RESULT_CODE_NOTE_PHOTO_VIEW) {
                        //..........................................................................................photo note
                        noteText = data.getStringExtra(Constants.ADD_NEW_NOTE_TEXT);
                        if (!(noteText.isEmpty()) && !(noteText.equals(" "))){
                            ColorStateList colorStateList = data.getParcelableExtra(Constants.ADD_NEW_NOTE_BACKGROUND);
                            Uri photoUri = data.getParcelableExtra(Constants.ADD_NEW_NOTE_PHOTO);
                            PhotoNoteItem photoNoteItem = new PhotoNoteItem(noteText, colorStateList, photoUri);
                            noteItemList.add(photoNoteItem);
                            addNotesAdapter.notifyItemInserted(noteItemList.size() - 1);
                        } else
                            Toast.makeText(this, R.string.no_text, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(this, R.string.didnt_add_note, Toast.LENGTH_SHORT).show();
                    }

                }

            } else {
                if (resultCode == RESULT_OK){
                    ColorStateList colorStateList;
                    switch (requestCode){
                        case Constants.CHANGE_NOTE_DETAILS:
                            noteText = data.getStringExtra(Constants.RETURN_TEXT_TO_MAIN);
                            colorStateList = data.getParcelableExtra(Constants.RETURN_BACKGROUND_TO_MAIN);
                            noteItemList.set(myPosition, new NoteItem(noteText, colorStateList));
                            addNotesAdapter.notifyDataSetChanged();
                            break;
                        case Constants.CHANGE_NOTE_CHECK_DETAILS:
                            noteText = data.getStringExtra(Constants.RETURN_TEXT_TO_MAIN);
                            colorStateList = data.getParcelableExtra(Constants.RETURN_BACKGROUND_TO_MAIN);
                            int isChecked = data.getIntExtra(Constants.RETURN_IS_CHECKED_TO_MAIN, 3);
                            CheckNoteItem checkNoteItem = new CheckNoteItem(noteText, colorStateList, isChecked);
                            noteItemList.set(myPosition, checkNoteItem);
                            break;
                        case Constants.CHANGE_NOTE_PHOTO_DETAILS:
                            noteText = data.getStringExtra(Constants.RETURN_TEXT_TO_MAIN);
                            colorStateList = data.getParcelableExtra(Constants.RETURN_BACKGROUND_TO_MAIN);
                            Uri photoUri = data.getParcelableExtra(Constants.RETURN_PHOTO_URI_TO_MAIN);
                            PhotoNoteItem photoNoteItem = new PhotoNoteItem(noteText, colorStateList, photoUri);
                            noteItemList.set(myPosition, photoNoteItem);
                            addNotesAdapter.notifyDataSetChanged();
                            break;
                        default:
                            Toast.makeText(this, R.string.didnt_change, Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }
    }
    private void addNewNote(){
        Intent intent = new Intent(MainActivity.this, ActivityAddNewNote.class);
        startActivityForResult(intent, Constants.ADE_NOTE);
    }
    private void changeNoteItem(int position){
        myPosition = position;
        NoteItem noteItem = noteItemList.get(position);
        Intent intent = new Intent(MainActivity.this, ActivityNoteDetails.class);
        intent.putExtra(Constants.EXTRA_BACKGROUND_TO_ACTIVITY, noteItem.getColorStateList());
        intent.putExtra(Constants.EXTRA_NOTE_TEXT_TO_ACTIVITY, noteItem.getNoteText());
        startActivityForResult(intent, Constants.CHANGE_NOTE_DETAILS);
    }
    private void changeCheckNoteItem(int position){
        myPosition = position;
        CheckNoteItem checkNoteItem = (CheckNoteItem) noteItemList.get(position);
        Intent intent = new Intent(MainActivity.this, ActivityNoteCheckDetails.class);
        intent.putExtra(Constants.EXTRA_BACKGROUND_TO_ACTIVITY, checkNoteItem.getColorStateList());
        intent.putExtra(Constants.EXTRA_NOTE_TEXT_TO_ACTIVITY, checkNoteItem.getNoteText());
        intent.putExtra(Constants.EXTRA_IS_CHECKED_TO_ACTIVITY, checkNoteItem.getIsChecked());
        startActivityForResult(intent, Constants.CHANGE_NOTE_CHECK_DETAILS);
    }
    private void changePhotoNoteItem(int position){
        myPosition = position;
        PhotoNoteItem photoNoteItem = (PhotoNoteItem) noteItemList.get(position);
        Intent intent = new Intent(MainActivity.this, ActivityNotePhotoDetails.class);
        intent.putExtra(Constants.EXTRA_NOTE_TEXT_TO_ACTIVITY, photoNoteItem.getNoteText());
        intent.putExtra(Constants.EXTRA_BACKGROUND_TO_ACTIVITY, photoNoteItem.getColorStateList());
        intent.putExtra(Constants.EXTRA_PHOTO_URI_TO_ACTIVITY, photoNoteItem.getPhotoUri());
        startActivityForResult(intent, Constants.CHANGE_NOTE_PHOTO_DETAILS);
    }
    private void askForRemove(int position){
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                .setMessage(R.string.delet_confirmation)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        noteItemList.remove(position);
                        addNotesAdapter.notifyItemRemoved(position);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        alertDialog.show();
    }


}
