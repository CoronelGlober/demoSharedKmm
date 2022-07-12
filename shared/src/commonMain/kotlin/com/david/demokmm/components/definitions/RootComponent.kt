package com.david.demokmm.components.definitions

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.david.demokmm.utils.FlowWrapper
import com.kuuurt.paging.multiplatform.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Represents a component containing two children that are constantly updating its state,
 * it also contains the representation of each child state (counter and ascii character)
 */
interface RootComponent {

    val state: FlowWrapper<PagingData<Int>>

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