package com.rpfcoding.notestodoscontactsapp.auth.domain.repository

interface LoggerRepository {
    fun logNonFatalCrash(throwable: Throwable)
}