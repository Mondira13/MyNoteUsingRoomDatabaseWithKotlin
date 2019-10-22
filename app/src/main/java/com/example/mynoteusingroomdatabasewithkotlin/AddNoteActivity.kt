package com.example.mynoteusingroomdatabasewithkotlin

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mynoteusingroomdatabasewithkotlin.singletone.NoteSingletone
import kotlinx.android.synthetic.main.activity_add_note.*


class AddNoteActivity : AppCompatActivity() {

    lateinit var type : String
    lateinit var headingVal : String
    lateinit var descriptionVal : String
    lateinit var note: NoteEntity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        type = intent.getStringExtra("type")
        if(type.equals("add",true)){
            getSupportActionBar()?.setTitle("Add Note");
        }else  if(type.equals("edit",true)){
            getSupportActionBar()?.setTitle("Edit Note");
            if(intent.hasExtra("heading") && intent.hasExtra("description")) {
                headingVal = intent.getStringExtra("heading")
                descriptionVal = intent.getStringExtra("description")
                note = NoteSingletone.getInstance().note
                heading.setText(headingVal)
                description.setText(descriptionVal)
            }

        }
        setOnClickListener()
    }

    private fun setOnClickListener() {
        submit.setOnClickListener {
            if(type.equals("add",true)){
                addNewNote()
            }else  if(type.equals("edit",true)){
                editNewNote()
            }

        }
    }

    private fun editNewNote() {
        val noteHeading = heading.text.toString()
        val noteDescription = description.text.toString()

        if (noteHeading.isNotEmpty() && noteDescription.isNotEmpty()) {
            note.noteHeading = noteHeading
            note.noteDescription = noteDescription
        }

        class EditNote : AsyncTask<NoteEntity, Void, Boolean>() {

            override fun doInBackground(vararg params: NoteEntity?): Boolean {
                DatabaseClient(applicationContext).getInstance(getApplicationContext()).getAppDatabase().noteDao().update(note);
                return true
            }


            override fun onPostExecute(aVoid: Boolean) {
                super.onPostExecute(aVoid)
                finish()
                Toast.makeText(applicationContext, "Note Update successfully", Toast.LENGTH_SHORT).show();
                startActivity(Intent(applicationContext, MainActivity::class.java))
            }
        }

        val edit = EditNote()
        edit.execute()
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
