package com.indialone.indialonenotesapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import com.indialone.indialonenotesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var firebaseDbInstance: FirebaseDatabase
    private lateinit var firebaseDb: DatabaseReference
    private lateinit var notesAdapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        //firebase creation
        firebaseDbInstance = FirebaseDatabase.getInstance()
        firebaseDb = firebaseDbInstance.getReference(Constants.NOTES)

        val options = FirebaseRecyclerOptions.Builder<NoteEntity>()
            .setQuery(firebaseDb, NoteEntity::class.java)
            .build()

        notesAdapter = NotesAdapter(options)
        mBinding.notesRecyclerView.layoutManager = LinearLayoutManager(this)
        mBinding.notesRecyclerView.adapter = notesAdapter

        mBinding.addNoteFab.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        v?.id?.let {
            when (v.id) {
                R.id.addNoteFab -> {
                    startActivity(Intent(this@MainActivity, NewNoteActivity::class.java))
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        notesAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        notesAdapter.stopListening()
    }

}