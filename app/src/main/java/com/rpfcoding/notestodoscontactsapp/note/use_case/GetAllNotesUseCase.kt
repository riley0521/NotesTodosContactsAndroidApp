package com.rpfcoding.notestodoscontactsapp.note.use_case

import com.rpfcoding.notestodoscontactsapp.core.domain.model.Note
import com.rpfcoding.notestodoscontactsapp.core.domain.repository.NoteRepository
import com.rpfcoding.notestodoscontactsapp.core.util.NoteOrder
import com.rpfcoding.notestodoscontactsapp.core.util.OrderType
import com.rpfcoding.notestodoscontactsapp.core.util.Resource
import javax.inject.Inject

class GetAllNotesUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(order: NoteOrder): List<Note> {
        return when(val res = repository.getAll()) {
            is Resource.Error -> emptyList()
            is Resource.Success -> {
                when(order.orderType) {
                    OrderType.Ascending -> {
                        when(order) {
                            is NoteOrder.Color -> res.data.sortedBy { it.color }
                            is NoteOrder.Date -> res.data.sortedBy { it.createdAt }
                            is NoteOrder.Title -> res.data.sortedBy { it.title.lowercase() }
                        }
                    }
                    OrderType.Descending -> {
                        when(order) {
                            is NoteOrder.Color -> res.data.sortedByDescending { it.color }
                            is NoteOrder.Date -> res.data.sortedByDescending { it.createdAt }
                            is NoteOrder.Title -> res.data.sortedByDescending { it.title.lowercase() }
                        }
                    }
                }
            }
        }
    }
}