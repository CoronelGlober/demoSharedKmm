package com.david.demokmm.utils

import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

fun LifecycleOwner.coroutineScope(): CoroutineScope {
    val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    lifecycle.doOnDestroy(scope::cancel)
    return scope
}


interface Cancellable {
    fun cancel()
}