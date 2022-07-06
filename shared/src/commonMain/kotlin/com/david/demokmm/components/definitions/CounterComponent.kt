package com.david.demokmm.components.definitions

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.coroutines.flow.StateFlow

/**
 * Represents a section showing a constantly increasing number,
 * also allows to increment the counter by 10 with its dedicated function
 */
public interface CounterComponent {
    /**
     * Collectable state containing the numeric value being increased
     */
    val state: StateFlow<CounterState>

    /**
     * Increases the counter value by 10
     */
    fun incrementCounterBy10()
}

@Parcelize
data class CounterState(val count: Int = 0) : Parcelable