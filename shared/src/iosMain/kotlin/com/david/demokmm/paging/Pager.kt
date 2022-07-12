package com.david.demokmm.paging

import com.david.demokmm.paging.helpers.cachedIn
import com.david.demokmm.utils.FlowWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

/**
 * Copyright 2020, Kurt Renzo Acosta, All rights reserved.
 *
 * @author Kurt Renzo Acosta
 * @since 06/11/2020
 */

@OptIn(ExperimentalCoroutinesApi::class)
actual class Pager<Key : Any, Type : Any> actual constructor(
    private val clientScope: CoroutineScope,
    private val config: PagingConfig,
    private val initialKey: Key,
    private val getItems: suspend (Key, Int) -> PagingResult<Key, Type>
) :PagedData<Type>{

    private val _pagingData = MutableStateFlow<PagingData<Type>?>(null)
    override  val pagingData: FlowWrapper<PagingData<Type>> get() = FlowWrapper(_pagingData.filterNotNull().cachedIn(clientScope))

    private val _hasNextPage = MutableStateFlow(true)
    override val hasNextPage: Boolean
        get() = _hasNextPage.value

    private val currentPagingResult: MutableStateFlow<PagingResult<Key, Type>?> = MutableStateFlow(null)

    init {
        loadNext()
    }

    fun refresh() {
        currentPagingResult.value = null
        _hasNextPage.value = false
        loadNext()
    }

    fun loadPrevious() {
        loadItems(LoadDirection.PREVIOUS)
    }

    override fun loadNext() {
        loadItems(LoadDirection.NEXT)
    }

    private fun loadItems(loadDirection: LoadDirection) {
        val pagingResult = currentPagingResult.value
        val key = if (pagingResult == null) {
            initialKey
        } else {
            when (loadDirection) {
                LoadDirection.NEXT -> pagingResult.nextKey()
                LoadDirection.PREVIOUS -> pagingResult.prevKey()
            }
        }

        if (key != null && hasNextPage) {
            clientScope.launch {
                val newPagingResult = getItems(key, config.pageSize)
                _pagingData.value = _pagingData.value?.toMutableList()?.apply {
                    addAll(newPagingResult.items)
                }?.toPagingData() ?: newPagingResult.items.toPagingData()
                _hasNextPage.value = newPagingResult.items.size >= config.pageSize
                currentPagingResult.value = newPagingResult
            }
        }
    }

    enum class LoadDirection {
        PREVIOUS,
        NEXT
    }
}