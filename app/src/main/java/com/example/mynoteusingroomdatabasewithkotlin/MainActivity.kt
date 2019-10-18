package com.example.mynoteusingroomdatabasewithkotlin

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getNotes();
        setOnClickListener()
        listId.setLayoutManager(LinearLayoutManager(this));

    }

    private fun getNotes() {

        class GetNotes : AsyncTask<Void, Void, List<NoteEntity>>() {

            override fun doInBackground(vararg params: Void?): List<NoteEntity> {
                val noteEntity: List<NoteEntity> = DatabaseClient(applicationContext).getInstance(applicationContext).getAppDatabase().noteDao().getAll()
                return noteEntity
            }

            protected override fun onPostExecute(notes: List<NoteEntity>) {
                super.onPostExecute(notes)
                val adapter = NoteAdapter(this@MainActivity, notes)
                listId.setAdapter(adapter)
            }
        }

        val getNotesss = GetNotes()
        getNotesss.execute()

    }

    private fun setOnClickListener() {
        addNote.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }
    }
}
