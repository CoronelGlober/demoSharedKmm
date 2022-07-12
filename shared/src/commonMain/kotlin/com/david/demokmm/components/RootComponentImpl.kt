package com.david.demokmm.components

import com.arkivanov.decompose.ComponentContext
import com.david.demokmm.components.definitions.RootComponent
import com.david.demokmm.paging.*
import com.david.demokmm.utils.FlowWrapper
import com.david.demokmm.utils.coroutineScope
import com.david.demokmm.paging.helpers.cachedIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay

private const val ROOT_COMPONENT_STATE = "ROOT_COMPONENT_STATE"

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class RootComponentImpl constructor(componentContext: ComponentContext) :
    RootComponent, ComponentContext by componentContext {

    private val scope = coroutineScope()

    private val pagingConfig = PagingConfig(pageSize = 20, enablePlaceholders = false)


    override val state: PagedData<Int> = Pager(clientScope = scope, config = pagingConfig, initialKey = 1,
        getItems = { currentKey, size ->

            delay(5000)
            val lastValue = currentKey - 1 + size
            val elements = (currentKey..lastValue).toList()

            PagingResult(
                items = elements,
                currentKey = currentKey,
                prevKey = { null },
                nextKey = { lastValue + 1 }
            )
        }
    )
}