package com.david.demokmm.components.definitions

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.coroutines.flow.StateFlow

/**
 * Represents a section showing a constantly changing ascii character,
 * also allows to increment the ascii representation by 10 with its dedicated function
 */
interface AsciiComponent {
    /**
     * Collectable state containing the ascii character being changed
     */
    val state: StateFlow<AsciiState>

    /**
     * Increments the ascii representation by 10, e.g:
     * 70 ascii representation -> F
     * 80 ascii representation -> P
     */
    fun incrementAsciiValueBy10()
}

@Parcelize
data class AsciiState(val character: Char = 0.toChar()) : Parcelable