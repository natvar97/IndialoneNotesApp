package com.indialone.indialonenotesapp

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.indialone.indialonenotesapp.databinding.NotesItemBinding

class NotesAdapter(
    private val options: FirebaseRecyclerOptions<NoteEntity>
) : FirebaseRecyclerAdapter<NoteEntity, NotesAdapter.NotesViewHolder>(options) {

    private var notes = ArrayList<NoteEntity>()

    class NotesViewHolder(itemView: NotesItemBinding) : RecyclerView.ViewHolder(itemView.root) {
        val title = itemView.tvTitle
        val description = itemView.tvDescription
        val ivDelete = itemView.ivDelete
        val ivEdit = itemView.ivEdit
    }


    fun addData(list: List<NoteEntity>) {
        notes.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val view = NotesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int, model: NoteEntity) {
        holder.title.text = model.title
        holder.description.text = model.description
        holder.ivDelete.setOnClickListener {
            NotesAdapter(options).getRef(position).removeValue()
        }
        holder.ivEdit.setOnClickListener {
            val intent = Intent(holder.itemView.context , NewNoteActivity::class.java)
            intent.putExtra(Constants.NOTE , model)
            holder.itemView.context.startActivity(intent)
        }
    }
}