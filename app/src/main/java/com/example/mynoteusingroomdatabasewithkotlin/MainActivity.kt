package com.example.mynoteusingroomdatabasewithkotlin

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynoteusingroomdatabasewithkotlin.adapter.NoteAdapter
import kotlinx.android.synthetic.main.activity_main.*
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog


class MainActivity : AppCompatActivity() {
    lateinit var note : NoteEntity
    lateinit var adapter : NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listId.setLayoutManager(LinearLayoutManager(this));
        getNotes();
        setOnClickListener()
    }

    private fun getNotes() {

        class GetNotes : AsyncTask<Void, Void, List<NoteEntity>>() {

            override fun doInBackground(vararg params: Void?): List<NoteEntity> {
                val noteEntity: List<NoteEntity> = DatabaseClient(applicationContext).getInstance(applicationContext).getAppDatabase().noteDao().getAll()
                return noteEntity
            }

            protected override fun onPostExecute(notes: List<NoteEntity>) {
                super.onPostExecute(notes)
                adapter = NoteAdapter(this@MainActivity, notes)
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


        listId.addOnItemTouchListener(RecyclerItemClickListener(this, listId, object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int, motionEvent : MotionEvent) {
                       val deleteNote : ImageView = view.findViewById(R.id.deleteNote)
                        val editNote : ImageView = view.findViewById(R.id.editNote)


                        if (RecyclerItemClickListener.isViewClicked(deleteNote, motionEvent)) {
                            val builder = AlertDialog.Builder(this@MainActivity)
                            builder.setTitle("Are you want to delete this note?")
                            builder.setPositiveButton("Yes",
                                DialogInterface.OnClickListener { dialogInterface, i ->
                                    note = adapter.getNoteList().get(position)
                                    deleteMyNotes(note)
                                })
                            builder.setNegativeButton("No",
                                DialogInterface.OnClickListener { dialogInterface, i -> })

                            val ad = builder.create()
                            ad.show()

                        }
                        else if (RecyclerItemClickListener.isViewClicked(editNote, motionEvent)) {
                            editMyNote()
                        }

                    }

                    override fun onLongItemClick(view: View?, position: Int) {}

                })
        )



    }

    private fun editMyNote() {
        Toast.makeText(applicationContext, "Work in progress", Toast.LENGTH_SHORT).show()
    }

    private fun deleteMyNotes(notes: NoteEntity?) {
        class DeleteNotes : AsyncTask<Void, Void, Void>() {

            override fun doInBackground(vararg voids: Void): Void? {
                if (notes != null) {
                    DatabaseClient(applicationContext).getInstance(applicationContext).getAppDatabase().noteDao().delete(notes)
                }else{
                    Toast.makeText(applicationContext, "Error occure..!!", Toast.LENGTH_SHORT).show()
                }
                return null
            }

            override fun onPostExecute(aVoid: Void) {
                super.onPostExecute(aVoid)
                Toast.makeText(applicationContext, "Note Deleted..!!", Toast.LENGTH_LONG).show()
                finish()
            }
        }

        val dt = DeleteNotes()
        dt.execute()

    }
}
