package com.indialone.indialonenotesapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NoteEntity(
    var id : String? = null,
    var title: String? = null,
    var description: String? = null
) : Parcelable
