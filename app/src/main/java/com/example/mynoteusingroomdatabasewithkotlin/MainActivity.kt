package com.example.mynoteusingroomdatabasewithkotlin

import android.content.DialogInterface
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynoteusingroomdatabasewithkotlin.adapter.NoteAdapter
import com.example.mynoteusingroomdatabasewithkotlin.singletone.NoteSingletone
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var note: NoteEntity
    lateinit var adapter: NoteAdapter
    lateinit var noteEntity: List<NoteEntity>

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
                noteEntity = DatabaseClient(applicationContext).getInstance(applicationContext)
                    .getAppDatabase().noteDao().getAll()
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
            intent.putExtra("type", "add");
            startActivity(intent)
        }


        listId.addOnItemTouchListener(
            RecyclerItemClickListener(
                this,
                listId,
                object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int, motionEvent: MotionEvent) {
                        val deleteNote: ImageView = view.findViewById(R.id.deleteNote)
                        val editNote: ImageView = view.findViewById(R.id.editNote)


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

                        } else if (RecyclerItemClickListener.isViewClicked(editNote, motionEvent)) {
                            var heading: String? = noteEntity.get(position).noteHeading
                            var description: String? = noteEntity.get(position).noteDescription
                            note = adapter.getNoteList().get(position)
                            editMyNote(heading,description,note)
                        }

                    }

                    override fun onLongItemClick(view: View?, position: Int) {}

                })
        )


    }

    private fun editMyNote(
        heading: String?,
        description: String?,
        note: NoteEntity
    ) {
        val intent = Intent(this, AddNoteActivity::class.java)
        intent.putExtra("type", "edit");
        intent.putExtra("heading", heading);
        intent.putExtra("description", description);
        NoteSingletone.getInstance().note = note
        startActivity(intent)
    }

    private fun deleteMyNotes(notes: NoteEntity?) {
        class DeleteNotes : AsyncTask<Void, Void, Boolean>() {

            override fun doInBackground(vararg voids: Void): Boolean {
                if (notes != null) {
                    DatabaseClient(applicationContext).getInstance(applicationContext)
                        .getAppDatabase().noteDao().delete(notes)
                } else {
                    Toast.makeText(applicationContext, "Error occure..!!", Toast.LENGTH_SHORT)
                        .show()
                }
                return true
            }

            override fun onPostExecute(aVoid: Boolean) {
                super.onPostExecute(aVoid)
                Toast.makeText(applicationContext, "Note Deleted..!!", Toast.LENGTH_LONG).show()
                finish()
            }
        }

        val dt = DeleteNotes()
        dt.execute()

    }
}
