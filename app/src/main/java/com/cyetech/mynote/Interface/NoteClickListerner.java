package com.cyetech.mynote.Interface;

import androidx.cardview.widget.CardView;

import com.cyetech.mynote.Model.Notes;

public interface NoteClickListerner {
    void onClick(Notes motes);
    void onLongPress(Notes notes, CardView cardView);
}
