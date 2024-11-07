package com.cyetech.mynote;

import static android.app.ProgressDialog.show;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.cyetech.mynote.Adapter.NoteListAdapter;
import com.cyetech.mynote.DataBase.RoomDB;
import com.cyetech.mynote.DataBase.RoomDB_Impl;
import com.cyetech.mynote.Interface.NoteClickListerner;
import com.cyetech.mynote.Model.Notes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{
    RecyclerView recyclerView;
    NoteListAdapter noteListAdapter;
    RoomDB database;
    List<Notes> notes = new ArrayList<>();

    FloatingActionButton fabBtn;
    SearchView searchView;
    Notes selectedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.noteRv);
        fabBtn = findViewById(R.id.addBtn);
        searchView = findViewById(R.id.searchView);
        database = RoomDB.getInstance(this);

        notes = database.mainDAO().getAll();
        updateRecycle(notes);

        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotesTakeActivity.class);
                startActivityForResult(intent, 101);

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }

    private void filter(String newtext){
        List<Notes> filterList = new ArrayList<>();
        for (Notes singleNote : notes) {
            if(singleNote.getTitle().toLowerCase().contains(newtext.toLowerCase())
                    || singleNote.getNote().toLowerCase().contains(newtext.toLowerCase())){
                filterList.add(singleNote);
            }
        }
        noteListAdapter.filterList(filterList);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NotNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101){
            if (resultCode == MainActivity.RESULT_OK) {
                Notes new_notes = (Notes) data.getSerializableExtra("note");
                database.mainDAO().insert(new_notes);
                notes.clear();
                notes.addAll(database.mainDAO().getAll());
                noteListAdapter.notifyDataSetChanged();
            }
        }else if (requestCode == 102){
            if (resultCode == Activity.RESULT_OK){
                Notes new_notes = (Notes) data.getSerializableExtra("note");
                database.mainDAO().update(new_notes.getId(), new_notes.getTitle(), new_notes.getNote());
                notes.clear();
                notes.addAll(database.mainDAO().getAll());
                noteListAdapter.notifyDataSetChanged();

            }
        }
    }
    public void updateRecycle(List<Notes> notes){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        noteListAdapter = new NoteListAdapter(MainActivity.this, notes, noteClickListerner);
        recyclerView.setAdapter(noteListAdapter);
    }
    public final NoteClickListerner noteClickListerner = new NoteClickListerner() {
        @Override
        public void onClick(Notes notes) {
        Intent intent = new Intent(MainActivity.this, NotesTakeActivity.class);
        intent.putExtra("old_note", notes);
        startActivityForResult(intent, 102);
        }

        @Override
        public void onLongPress(Notes notes, CardView cardView) {
            selectedNote = new Notes();
            selectedNote = notes;
            showPop(cardView);

        }
    };

    private void showPop(CardView cardView) {
        PopupMenu popupMenu = new PopupMenu(this, cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        if (item.getItemId() == R.id.pin) {
                if (selectedNote.getPinned()){
                    database.mainDAO().pin(selectedNote.getId(), false);
                    Toast.makeText(this, "Unpinned", Toast.LENGTH_SHORT).show();
            } else {
            database.mainDAO().pin(selectedNote.getId(), true);
            Toast.makeText(this, "Pinned", Toast.LENGTH_SHORT).show();
        }
                notes.clear();
                notes.addAll(database.mainDAO().getAll());
                noteListAdapter.notifyDataSetChanged();
                return true;
            }else if (item.getItemId() == R.id.delete) {
                database.mainDAO().delete(selectedNote);
                notes.remove(selectedNote);
                noteListAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;

    }
}