package com.cyetech.mynote.Adapter;


import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cyetech.mynote.Interface.NoteClickListerner;
import com.cyetech.mynote.Model.Notes;
import com.cyetech.mynote.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {

    Context context;
    List<Notes> notesList;

    public NoteListAdapter(Context context, List<Notes> notesList, NoteClickListerner listerner) {
        this.context = context;
        this.notesList = notesList;
        this.listerner = listerner;
    }

    NoteClickListerner listerner;


    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(context).inflate(R.layout.notes_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.titleTxt.setText(notesList.get(position).getTitle());
        holder.noteTxt.setText(notesList.get(position).getNote());
        holder.dateTxt.setText(notesList.get(position).getDate());
        holder.dateTxt.setSelected(true);

        if (notesList.get(position).getPinned()) {
            holder.imageView.setImageResource(R.drawable.pin);
        } else {
            holder.imageView.setImageResource(0);
        }
        int color_code = getRandomColour();
        holder.cardView.setCardBackgroundColor(holder.itemView.getResources().getColor(color_code));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listerner.onClick(notesList.get(holder.getAdapterPosition()));
            }
        });
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listerner.onLongPress(notesList.get(holder.getAdapterPosition()), holder.cardView);
                return true;
            }
        });
    }

    private int getRandomColour() {
        List<Integer> colorCode = new ArrayList<>();
        colorCode.add(R.color.color1);
        colorCode.add(R.color.color2);
        colorCode.add(R.color.color3);
        colorCode.add(R.color.color4);
        colorCode.add(R.color.color5);
        colorCode.add(R.color.color6);
        colorCode.add(R.color.color7);
        colorCode.add(R.color.color8);
        colorCode.add(R.color.color9);
        colorCode.add(R.color.color10);
        colorCode.add(R.color.color11);
        colorCode.add(R.color.color12);

        Random random = new Random();
        int random_color = random.nextInt(colorCode.size());
        return colorCode.get(random_color);
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public void filterList(List<Notes> filterList) {
        notesList = filterList;
        notifyDataSetChanged();

}

class NoteViewHolder extends RecyclerView.ViewHolder{
    CardView cardView;
    TextView noteTxt, dateTxt, titleTxt;
    ImageView imageView;

    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);

        cardView = itemView.findViewById(R.id.note_container);
        noteTxt = itemView.findViewById(R.id.noteTxt);
        dateTxt = itemView.findViewById(R.id.dateTxt);
        titleTxt = itemView.findViewById(R.id.titleTxt);
        imageView = itemView.findViewById(R.id.pinned);

        }
    }
}