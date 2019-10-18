package com.example.mynoteusingroomdatabasewithkotlin

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add_note.*
import android.widget.Toast
import android.content.Intent
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import kotlinx.android.synthetic.main.activity_main.*


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
        var noteHeading = heading.text.toString()
        var noteDescription = description.text.toString()


        class SaveNote : AsyncTask<Void, Void, Void>() {

            override fun doInBackground(vararg params: Void?): Void? {
                val noteEntity = NoteEntity()
                noteEntity.noteHeading = noteHeading
                noteEntity.noteDescription = noteDescription

                DatabaseClient(applicationContext).getInstance(getApplicationContext()).getAppDatabase().noteDao().insert(noteEntity);
                return null;
            }

            override fun onPostExecute(aVoid: Void) {
                super.onPostExecute(aVoid)
                finish()
                startActivity(Intent(applicationContext, MainActivity::class.java))
                Toast.makeText(applicationContext, "Saved", Toast.LENGTH_LONG).show()
            }
        }

        val save = SaveNote()
        save.execute()

    }



}
