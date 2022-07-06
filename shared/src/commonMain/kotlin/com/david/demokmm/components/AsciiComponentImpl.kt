package com.david.demokmm.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.statekeeper.consume
import com.david.demokmm.components.definitions.AsciiComponent
import com.david.demokmm.components.definitions.AsciiState
import com.david.demokmm.components.definitions.RootComponent
import com.david.demokmm.utils.coroutineScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

private const val ASCII_COMPONENT_STATE = "ASCII_COMPONENT_STATE"

class AsciiComponentImpl(
    context: ComponentContext,
    actionsListener: (RootComponent.ChildAction) -> Unit
) : AsciiComponent, ComponentContext by context {

    private val scope = coroutineScope()

    private val handler = instanceKeeper.getOrCreate(ASCII_COMPONENT_STATE) {
        Handler(
            stateKeeper.consume(ASCII_COMPONENT_STATE) ?: AsciiState(character = 30.toChar()),
            scope,
            actionsListener
        )
    }

    override val state: StateFlow<AsciiState> = handler.state

    override fun incrementAsciiValueBy10() {
        handler.increment()
    }

    init {
        stateKeeper.register(ASCII_COMPONENT_STATE) { handler.state.value }
    }

    private class Handler(
        initialState: AsciiState,
        val scope: CoroutineScope,
        val actionsListener: (RootComponent.ChildAction) -> Unit
    ) :
        InstanceKeeper.Instance {

        val state = MutableStateFlow(initialState)

        init {
            scope.launch {
                while (scope.isActive) {
                    delay(1500)
                    state.value =
                        state.value.copy(character = (state.value.character.code + 1).toChar())
                    actionsListener(RootComponent.ChildAction.UpdateAsciiCharacter(state.value.character))
                }
            }
        }

        fun increment() {
            state.value = state.value.copy(character = (state.value.character.code + 10).toChar())
            actionsListener(RootComponent.ChildAction.UpdateAsciiCharacter(state.value.character))
        }

        override fun onDestroy() {
            scope.cancel()
        }
    }
}

