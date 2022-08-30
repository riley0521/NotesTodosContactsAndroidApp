package com.rpfcoding.notestodoscontactsapp.note.domain.model

import android.os.Parcelable
import com.rpfcoding.notestodoscontactsapp.ui.theme.*
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(
    val title: String,
    val description: String,
    val color: Int,
    val createdAt: String,
    val id: String = ""
): Parcelable {
    companion object {
        val noteColors = listOf(
            RedOrange,
            LightGreen,
            Violet,
            BabyBlue,
            RedPink
        )
    }
}

class InvalidNoteException(message: String): Exception(message)
