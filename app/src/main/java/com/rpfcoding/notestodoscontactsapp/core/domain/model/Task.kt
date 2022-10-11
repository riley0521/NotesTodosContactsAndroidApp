package com.rpfcoding.notestodoscontactsapp.core.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
@Entity(tableName = "tbl_tasks")
data class Task(
    val title: String,
    /**
     * 0 - None
     * 1 - Low
     * 2 - Medium
     * 3 - High
     */
    val priority: Int,
    val dueDate: Long,
    val description: String,
    val flag: Boolean = false,
    val completed: Boolean = false,
    @PrimaryKey
    val id: String = ""
): Parcelable {
    val dateFromMillis: Date? get() {
        return try {
            val c = Calendar.getInstance()
            c.timeInMillis = dueDate

            c.time
        } catch (e: Exception) {
            null
        }
    }

    val formattedDate: String? get() {
        return try {
            val c = Calendar.getInstance()
            c.timeInMillis = dueDate

            SimpleDateFormat("MMMM dd, yyyy hh:mm a", Locale.getDefault()).format(c.time)
        } catch (e: Exception) {
            null
        }
    }
}
