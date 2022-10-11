package com.rpfcoding.notestodoscontactsapp.core.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}