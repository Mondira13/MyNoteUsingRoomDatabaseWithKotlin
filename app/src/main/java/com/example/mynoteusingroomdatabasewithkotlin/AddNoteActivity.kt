package com.example.mynoteusingroomdatabasewithkotlin

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_note.*


class AddNoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        setOnClickListener()
    }

    private fun setOnClickListener() {
        submit.setOnClickListener {
            addNewNote()
        }
    }

    private fun addNewNote() {
        val noteHeading = heading.text.toString()
        val noteDescription = description.text.toString()

        val noteEntity = NoteEntity()
        if (noteHeading.isNotEmpty() && noteDescription.isNotEmpty()) {
            noteEntity.noteHeading = noteHeading
            noteEntity.noteDescription = noteDescription
        }

        class SaveNote : AsyncTask<NoteEntity, Void, Boolean>() {

            override fun doInBackground(vararg params: NoteEntity?): Boolean {
                DatabaseClient(applicationContext).getInstance(getApplicationContext()).getAppDatabase().noteDao().insert(noteEntity);
                return true
            }

            override fun onPostExecute(aVoid: Boolean) {
                super.onPostExecute(aVoid)
                finish()
                Toast.makeText(applicationContext, "Note Added successfully", Toast.LENGTH_SHORT).show();
                startActivity(Intent(applicationContext, MainActivity::class.java))
            }
        }

        val save = SaveNote()
        save.execute()

    }

}
