package com.rpfcoding.notestodoscontactsapp.note.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rpfcoding.notestodoscontactsapp.note.data.local.entity.NoteEntity
import com.rpfcoding.notestodoscontactsapp.note.domain.util.NoteOrder
import com.rpfcoding.notestodoscontactsapp.note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    fun getNotes(order: NoteOrder): Flow<List<NoteEntity>> {
        when (order) {
            is NoteOrder.Color -> {
                return when (order.orderType) {
                    OrderType.Ascending -> {
                        getAllByColorAsc()
                    }
                    OrderType.Descending -> {
                        getAllByColorDesc()
                    }
                }
            }
            is NoteOrder.Date -> {
                return when (order.orderType) {
                    OrderType.Ascending -> {
                        getAllByDateAsc()
                    }
                    OrderType.Descending -> {
                        getAllByDateDesc()
                    }
                }
            }
            is NoteOrder.Title -> {
                return when (order.orderType) {
                    OrderType.Ascending -> {
                        getAllByTitleAsc()
                    }
                    OrderType.Descending -> {
                        getAllByTitleDesc()
                    }
                }
            }
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteEntity)

    @Query("SELECT * FROM tbl_notes ORDER BY color ASC")
    fun getAllByColorAsc(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM tbl_notes ORDER BY color DESC")
    fun getAllByColorDesc(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM tbl_notes ORDER BY createdAt ASC")
    fun getAllByDateAsc(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM tbl_notes ORDER BY createdAt DESC")
    fun getAllByDateDesc(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM tbl_notes ORDER BY title ASC")
    fun getAllByTitleAsc(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM tbl_notes ORDER BY title DESC")
    fun getAllByTitleDesc(): Flow<List<NoteEntity>>

    @Query("DELETE FROM tbl_notes WHERE id = :id")
    suspend fun deleteById(id: String)
}