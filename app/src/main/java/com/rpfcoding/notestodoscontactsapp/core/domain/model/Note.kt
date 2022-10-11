package com.rpfcoding.notestodoscontactsapp.core.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rpfcoding.notestodoscontactsapp.ui.theme.*
import kotlinx.parcelize.Parcelize
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
@Entity(tableName = "tbl_notes")
data class Note(
    val title: String,
    val description: String,
    val color: Int,
    val createdAt: Long = 0,
    @PrimaryKey
    val id: String = ""
): Parcelable {
    val formattedDate: String? get() {
        return try {
            val c = Calendar.getInstance()
            c.timeInMillis = createdAt

            SimpleDateFormat("MMMM dd, yyyy hh:mm a", Locale.getDefault()).format(c.time)
        } catch (e: Exception) {
            null
        }
    }
}




