package com.example.mynoteusingroomdatabasewithkotlin.singletone;

import com.example.mynoteusingroomdatabasewithkotlin.NoteEntity;

public class NoteSingletone {
    private static NoteSingletone instance;

    private NoteEntity note;

    public static NoteSingletone getInstance(){
        if (instance == null){
            instance = new NoteSingletone();
        }
        return instance;
    }

    public NoteEntity getNote() {
        return note;
    }

    public void setNote(NoteEntity note) {
        this.note = note;
    }
}
