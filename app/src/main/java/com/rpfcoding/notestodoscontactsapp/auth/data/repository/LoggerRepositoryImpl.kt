package com.rpfcoding.notestodoscontactsapp.auth.data.repository

import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.rpfcoding.notestodoscontactsapp.auth.domain.repository.LoggerRepository
import javax.inject.Inject

class LoggerRepositoryImpl @Inject constructor() : LoggerRepository {
    override fun logNonFatalCrash(throwable: Throwable) {
        Firebase.crashlytics.recordException(throwable)
    }
}