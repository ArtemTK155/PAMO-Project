package edu.ufp.pamfinalproject.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface Note_Dao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveNote(note : Note_Entity)

    @Query("select * from Note_Entity")
    fun getNotes() : List<Note_Entity>

    @Query("select * from Note_Entity")
    fun getAllNotes(): LiveData<List<Note_Entity>>

    @Query("SELECT * FROM Note_Entity WHERE _id=:id ")
    fun getNote(id: String): Note_Entity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllNotes(noteList: List<Note_Entity>)

    @Query("DELETE FROM Note_Entity WHERE _id=:id")
    fun deleteNote(id: String)

    @Query("DELETE FROM Note_Entity")
    fun deleteAll()
}