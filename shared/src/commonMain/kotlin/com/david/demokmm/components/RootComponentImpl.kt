package com.david.demokmm.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.essenty.statekeeper.consume
import com.david.demokmm.components.definitions.CounterComponent
import com.david.demokmm.components.definitions.AsciiComponent
import com.david.demokmm.components.definitions.RootComponent
import com.david.demokmm.components.definitions.RootComponent.ChildAction.UpdateCounter
import com.david.demokmm.components.definitions.RootComponent.ChildAction.UpdateAsciiCharacter
import com.david.demokmm.components.definitions.RootState
import kotlinx.coroutines.flow.MutableStateFlow

private const val ROOT_COMPONENT_STATE = "ROOT_COMPONENT_STATE"

class RootComponentImpl constructor(componentContext: ComponentContext) : RootComponent,
    ComponentContext by componentContext {

    private fun actionListener(action: RootComponent.ChildAction) {
        when (action) {
            is UpdateCounter -> state.value = state.value.copy(counter = action.count)
            is UpdateAsciiCharacter -> state.value = state.value.copy(character = action.character)
        }
    }

    override val state: MutableStateFlow<RootState> = MutableStateFlow(
        stateKeeper.consume(ROOT_COMPONENT_STATE) ?: RootState(0, '.')
    )

    init {
        stateKeeper.register(ROOT_COMPONENT_STATE) { state.value }
    }


    override val counterChild: CounterComponent =
        CounterComponentImpl(childContext("counter"), ::actionListener)
    override val lettersChild: AsciiComponent =
        AsciiComponentImpl(childContext("letters"), ::actionListener)
}