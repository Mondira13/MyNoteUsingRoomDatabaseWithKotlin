package com.example.mynoteusingroomdatabasewithkotlin

import android.content.Context
import androidx.room.Room



class DatabaseClient(mCtx: Context) {
    //our app database object
    private var noteDataBase: NoteDataBase
    private val mCtx: Context
    private var mInstance: DatabaseClient? = null

    init {
        this.mCtx = mCtx

        //creating the app database with Room database builder
        //MyToDos is the name of the database
        noteDataBase = Room.databaseBuilder(mCtx, NoteDataBase::class.java, "MyToDos").build()
    }

    @Synchronized
    fun getInstance(mCtx: Context): DatabaseClient {
        if (mInstance == null) {
            mInstance = DatabaseClient(mCtx)
        }
        return mInstance as DatabaseClient
    }

    fun getAppDatabase(): NoteDataBase {
        return noteDataBase
    }
}