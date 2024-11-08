package com.cyetech.mynote.DataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cyetech.mynote.Model.Notes;

import java.util.List;


@Dao
public interface MainDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Notes notes);

    @Query("SELECT * FROM notes_data ORDER BY pin DESC, id DESC")
    List<Notes> getAll();

    @Query("UPDATE notes_data SET title = :title, note = :note  WHERE ID = :id")
    void update(int id, String title, String note);

    @Delete
    void delete(Notes note);
    @Query("UPDATE notes_data SET pin = :pin  WHERE ID = :id")
    void pin(int id, boolean pin);
}
