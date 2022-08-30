package com.rpfcoding.notestodoscontactsapp.note.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}