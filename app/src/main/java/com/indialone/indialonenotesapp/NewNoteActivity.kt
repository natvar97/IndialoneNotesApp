package com.indialone.indialonenotesapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.indialone.indialonenotesapp.databinding.ActivityNewNoteBinding

class NewNoteActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mBinding: ActivityNewNoteBinding
    private lateinit var firebaseDbInstance: FirebaseDatabase
    private lateinit var firebaseDb: DatabaseReference
    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityNewNoteBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        firebaseDbInstance = FirebaseDatabase.getInstance()
        firebaseDb = firebaseDbInstance.getReference(Constants.NOTES)

        firebaseDbInstance.getReference(Constants.APP_TITLE_NODE).setValue(Constants.APP_TITLE)


        mBinding.saveNote.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        v?.id?.let {
            when (v.id) {
                R.id.save_note -> {
                    val title = mBinding.etTitle.text.toString().trim { it <= ' ' }
                    val description = mBinding.etDescription.text.toString().trim { it <= ' ' }

                    if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
                        Toast.makeText(this, "All fields required...", Toast.LENGTH_SHORT).show()
                    } else {

                        if (TextUtils.isEmpty(userId)) {
                            createNote(title, description)
                        } else {
                            updateNote(title, description)
                        }

                        startActivity(Intent(this@NewNoteActivity, MainActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }


    private fun createNote(title: String, description: String) {
        userId = firebaseDb.push().key
        val note = NoteEntity(title, description)
        firebaseDb.child(userId!!).setValue(note)
    }

    private fun updateNote(title: String, description: String) {
        if (!TextUtils.isEmpty(title)) {
            firebaseDb.child(userId!!).child(Constants.TITLE).setValue(title)
        }
        if (!TextUtils.isEmpty(description)) {
            firebaseDb.child(userId!!).child(Constants.DESCRIPTION).setValue(description)
        }
    }

}
