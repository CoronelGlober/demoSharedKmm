package com.david.demokmm.components.definitions

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Represents a component containing two children that are constantly updating its state,
 * it also contains the representation of each child state (counter and ascii character)
 */
interface RootComponent {

    val state: MutableStateFlow<RootState>

    val counterChild: CounterComponent
    val lettersChild: AsciiComponent

    /**
     * Actions being performed by the children components,
     * used for child -> parent communication
     */
    sealed class ChildAction {
        data class UpdateCounter(val count: Int) : ChildAction()
        data class UpdateAsciiCharacter(val character: Char) : ChildAction()
    }
}

@Parcelize
data class RootState(val counter: Int, val character: Char) : Parcelable