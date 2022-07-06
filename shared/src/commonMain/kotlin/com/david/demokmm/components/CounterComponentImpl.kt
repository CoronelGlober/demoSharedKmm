package com.david.demokmm.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.statekeeper.consume
import com.david.demokmm.components.definitions.CounterComponent
import com.david.demokmm.components.definitions.CounterState
import com.david.demokmm.components.definitions.RootComponent
import com.david.demokmm.utils.coroutineScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*


private const val COUNTER_COMPONENT_STATE = "COUNTER_COMPONENT_STATE"

class CounterComponentImpl(
    componentContext: ComponentContext,
    actionsListener: (RootComponent.ChildAction) -> Unit
) : CounterComponent, ComponentContext by componentContext {

    private val scope = coroutineScope()

    private val handler = instanceKeeper.getOrCreate(COUNTER_COMPONENT_STATE) {
        Handler(
            stateKeeper.consume(COUNTER_COMPONENT_STATE) ?: CounterState(count = 0),
            scope,
            actionsListener
        )
    }

    override val state: StateFlow<CounterState> = handler.state

    override fun incrementCounterBy10() {
        handler.increment()
    }

    init {
        stateKeeper.register(COUNTER_COMPONENT_STATE) { handler.state.value }
    }

    private class Handler(
        initialState: CounterState,
        val scope: CoroutineScope,
        val actionsListener: (RootComponent.ChildAction) -> Unit
    ) : InstanceKeeper.Instance {

        val state = MutableStateFlow(initialState)

        init {
            scope.launch {
                while (scope.isActive) {
                    delay(1000)
                    state.value = state.value.copy(count = state.value.count + 1)
                    actionsListener(RootComponent.ChildAction.UpdateCounter(state.value.count))
                }
            }
        }

        fun increment() {
            state.value = state.value.copy(count = state.value.count + 10)
            actionsListener(RootComponent.ChildAction.UpdateCounter(state.value.count))
        }

        override fun onDestroy() {
            scope.cancel()
        }
    }
}
