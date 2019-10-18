package com.example.mynoteusingroomdatabasewithkotlin

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Notes")
class NoteEntity : Serializable {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "noteHeading")
    var noteHeading: String? = null

    @ColumnInfo(name = "noteDescription")
    var noteDescription: String? = null

}
