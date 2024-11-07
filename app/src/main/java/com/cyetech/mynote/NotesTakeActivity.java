package com.cyetech.mynote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.cyetech.mynote.Model.Notes;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotesTakeActivity extends AppCompatActivity {
    EditText titleED, noteED;
    ImageView saveBtn;
    Notes notes;
    boolean isOldNote = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_take);
        saveBtn = findViewById(R.id.saveBtn);
        titleED = findViewById(R.id.titleEdt);
        noteED = findViewById(R.id.noteEdt);
        notes = new Notes();
        try {
            notes = (Notes) getIntent().getSerializableExtra("old_note");
//            assert notes != null;
            titleED.setText(notes.getTitle());
            noteED.setText(notes.getNote());
            isOldNote = true;
        }catch (Exception e){
            e.printStackTrace();
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOldNote) {
                    notes = new Notes();
                }
                String title = titleED.getText().toString();
                String description = noteED.getText().toString();

                if(description.isEmpty()){
                    Toast.makeText(NotesTakeActivity.this, "Please enter description", Toast.LENGTH_SHORT).show();
                    return;
                }
                SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyy HH:MM a");
                Date date = new Date();

                notes.setTitle(title);
                notes.setNote(description);
                notes.setDate(format.format(date));

                Intent intent = new Intent();
                intent.putExtra("note", notes);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

    }
}