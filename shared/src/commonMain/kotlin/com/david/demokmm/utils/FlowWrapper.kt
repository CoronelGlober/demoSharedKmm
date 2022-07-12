package com.david.demokmm.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class FlowWrapper<T>(private val origin: Flow<T>) : Flow<T> by origin {
    fun watch(callback: (T) -> Unit): Cancellable {
        val job = Job()

        onEach { callback(it) }
            .launchIn(CoroutineScope(Dispatchers.Main + job))

        return object : Cancellable {
            override fun cancel() {
                job.cancel()
            }
        }
    }
}