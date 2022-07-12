package com.david.demokmm.components

import com.arkivanov.decompose.ComponentContext
import com.david.demokmm.components.definitions.RootComponent
import com.david.demokmm.utils.FlowWrapper
import com.david.demokmm.utils.coroutineScope
import com.kuuurt.paging.multiplatform.Pager
import com.kuuurt.paging.multiplatform.PagingConfig
import com.kuuurt.paging.multiplatform.PagingData
import com.kuuurt.paging.multiplatform.PagingResult
import com.kuuurt.paging.multiplatform.helpers.cachedIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

private const val ROOT_COMPONENT_STATE = "ROOT_COMPONENT_STATE"

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class RootComponentImpl constructor(componentContext: ComponentContext) :
    RootComponent, ComponentContext by componentContext {

    private val scope = coroutineScope()

    private val pagingConfig = PagingConfig(pageSize = 20, enablePlaceholders = false)


    private val characterPager = Pager(clientScope = scope, config = pagingConfig, initialKey = 1,
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

    override val state: FlowWrapper<PagingData<Int>> = FlowWrapper(characterPager.pagingData.cachedIn(scope))

}