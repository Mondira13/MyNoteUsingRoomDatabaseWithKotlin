package com.example.mynoteusingroomdatabasewithkotlin

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

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

    override fun getItemCount(): Int {
       return notes.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val myViewHolder = holder as NoteAdapter.NoteAdapterViewHolder
        val notesItem = notes[position]
        myViewHolder.mHeading.setText(notesItem.noteHeading)
        myViewHolder.mDescription.setText(notesItem.noteDescription)
    }

    inner class NoteAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mHeading: TextView
        val mDescription: TextView

        init {
            mHeading = itemView.findViewById(R.id.heading)
            mDescription = itemView.findViewById(R.id.description)
        }
    }

}