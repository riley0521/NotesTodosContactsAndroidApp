package com.rpfcoding.notestodoscontactsapp.core.util

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow

fun getInternetConnectionStatusFlow(networkObserver: NetworkConnectivityObserver): Flow<Boolean> {
    return flow {
        while (true) {
            emit(networkObserver.hasInternet())
            delay(100L)
        }
    }.distinctUntilChanged()
}