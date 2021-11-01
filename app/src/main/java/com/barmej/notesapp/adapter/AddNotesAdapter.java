package com.barmej.notesapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.barmej.notesapp.R;
import com.barmej.notesapp.dataItem.CheckNoteItem;
import com.barmej.notesapp.dataItem.NoteItem;
import com.barmej.notesapp.dataItem.PhotoNoteItem;
import com.barmej.notesapp.interfase.OnClickItemListener;
import com.barmej.notesapp.interfase.OnLongClickItemListener;

import java.util.List;

public class AddNotesAdapter extends RecyclerView.Adapter<AddNotesAdapter.ViewHolder> {
    List<NoteItem> noteItemList;
    final private  OnClickItemListener onClickItemListener;
    final private OnLongClickItemListener onLongClickItemListener;

    private CheckViewHolder checkViewHolder;
    private PhotoViewHolder photoViewHolder;

    public AddNotesAdapter(List<NoteItem> noteItemList, OnClickItemListener onClickItemListener, OnLongClickItemListener onLongClickItemListener) {
        this.noteItemList = noteItemList;
        this.onClickItemListener = onClickItemListener;
        this.onLongClickItemListener = onLongClickItemListener;
    }

    @Override
    public int getItemViewType(int position) {
        NoteItem noteItem = noteItemList.get(position);

        if(noteItem instanceof PhotoNoteItem  ) {
            return 2;
        } else if (noteItem instanceof CheckNoteItem) {
            return 1;
        } else {
            return 0;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int itemType) {
        if (itemType == 0) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_note, viewGroup, false);
            return new ViewHolder(view);
        } else if (itemType == 1) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_note_check, viewGroup, false);
            checkViewHolder = new CheckViewHolder(view);
            return checkViewHolder;
        } else if (itemType == 2) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_note_photo, viewGroup, false);
            photoViewHolder = new PhotoViewHolder(view);
            return photoViewHolder;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        NoteItem noteItem = noteItemList.get(position);
        if(noteItem instanceof PhotoNoteItem) {
            photoViewHolder.onBindPhoto(position);
        } else if (noteItem instanceof CheckNoteItem) {
            checkViewHolder.onBindCheck(position);
        } else {
            viewHolder.onBindNote(position);
        }

    }


    @Override
    public int getItemCount() {

        return noteItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linear_layout_item);
            textView = itemView.findViewById(R.id.noteText_item_in_activity);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItemListener.onClickNoteItemListener(getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onLongClickItemListener.onLongClickItemListener(getAdapterPosition());
                    return true;
                }
            });


        }

        public void onBindNote(int position){
            NoteItem noteItem = noteItemList.get(position);
            linearLayout.setBackgroundTintList(noteItem.getColorStateList());
            textView.setText(noteItem.getNoteText());
        }

    }

    class CheckViewHolder extends  ViewHolder{
        CheckBox checkBox;
        public CheckViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkNote_item_in_avtivity);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItemListener.onClickCheckNoteItemListener(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onLongClickItemListener.onLongClickItemListener(getAdapterPosition());
                    return true;
                }
            });
        }

        public void  onBindCheck(int position){
            NoteItem noteItem = noteItemList.get(position);
            CheckNoteItem checkNoteItem = (CheckNoteItem) noteItem;
            linearLayout.setBackgroundTintList(noteItem.getColorStateList());
            textView.setText(noteItem.getNoteText());
            if (checkNoteItem.getIsChecked() == 1){
                checkBox.setChecked(true);
            } else if (checkNoteItem.getIsChecked() == 0){
                checkBox.setChecked(false);
            }
        }



    }

    class PhotoViewHolder extends ViewHolder {
        ImageView imageView;
        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.photoNote_item);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItemListener.onClickPhotoNoteItemListener(getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onLongClickItemListener.onLongClickItemListener(getAdapterPosition());
                    return true;
                }
            });
        }

        public void onBindPhoto(int position){
            NoteItem noteItem = noteItemList.get(position);
            PhotoNoteItem photoNoteItem = (PhotoNoteItem) noteItem;
            linearLayout.setBackgroundTintList(noteItem.getColorStateList());
            textView.setText(noteItem.getNoteText());
            imageView.setImageURI(photoNoteItem.getPhotoUri())




            ;
        }
    }


}
