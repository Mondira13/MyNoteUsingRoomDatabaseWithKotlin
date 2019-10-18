package com.example.mynoteusingroomdatabasewithkotlin

import androidx.room.*


@Dao
interface NoteDao {
    @Query("SELECT * FROM Notes")
    fun getAll(): List<NoteEntity>

    @Insert
    fun insert(noteEntity: NoteEntity)

    @Delete
    fun delete(noteEntity: NoteEntity)

    @Update
    fun update(noteEntity: NoteEntity)
}