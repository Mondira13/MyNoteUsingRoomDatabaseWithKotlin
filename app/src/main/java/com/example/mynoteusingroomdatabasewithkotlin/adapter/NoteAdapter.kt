package com.example.mynoteusingroomdatabasewithkotlin.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mynoteusingroomdatabasewithkotlin.NoteEntity
import com.example.mynoteusingroomdatabasewithkotlin.R

class NoteAdapter(activity: Activity, notes: List<NoteEntity>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private lateinit var activity: Activity
    private lateinit var notes: List<NoteEntity>

    init {
        this.activity = activity
        this.notes = notes
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapterViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.node_list_child, parent, false)
        return NoteAdapterViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val myViewHolder = holder as NoteAdapterViewHolder
        val notesItem = notes[position]
        myViewHolder.mHeading.setText(notesItem.noteHeading)
        myViewHolder.mDescription.setText(notesItem.noteDescription)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun getNoteList(): List<NoteEntity> {
        return notes
    }

    inner class NoteAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mHeading: TextView
        val mDescription: TextView
        val mDeleteNote : ImageView

        init {
            mHeading = itemView.findViewById(R.id.heading)
            mDescription = itemView.findViewById(R.id.description)
            mDeleteNote = itemView.findViewById(R.id.deleteNote)
        }
    }

}