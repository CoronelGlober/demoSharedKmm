package com.david.demokmm.paging

import androidx.paging.PagingState
import com.david.demokmm.paging.helpers.cachedIn
import com.david.demokmm.utils.FlowWrapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import androidx.paging.Pager as AndroidXPager

/**
 * Copyright 2020, Kurt Renzo Acosta, All rights reserved.
 *
 * @author Kurt Renzo Acosta
 * @since 06/11/2020
 */

@FlowPreview
@ExperimentalCoroutinesApi
actual class Pager<Key : Any, Type : Any> actual constructor(
    clientScope: CoroutineScope,
    config: PagingConfig,
    initialKey: Key,
    getItems: suspend (Key, Int) -> PagingResult<Key, Type>
) : PagedData<Type> {

    /**
     * Warning! not call it from android
     */
    override val hasNextPage: Boolean = false

    override fun loadNext() {}

    override val pagingData: FlowWrapper<PagingData<Type>> = FlowWrapper(AndroidXPager(
        config = config,
        pagingSourceFactory = {
            PagingSource(
                initialKey,
                getItems
            )
        }
    ).flow.cachedIn(clientScope))

    class PagingSource<K : Any, V : Any>(
        private val initialKey: K,
        private val getItems: suspend (K, Int) -> PagingResult<K, V>
    ) : androidx.paging.PagingSource<K, V>() {

        override val jumpingSupported: Boolean
            get() = true

        override val keyReuseSupported: Boolean
            get() = true

        override fun getRefreshKey(state: PagingState<K, V>): K? {
            return null
//            return state.anchorPosition?.let { position ->
//                state.closestPageToPosition(position)?.let { page ->
//                    page.prevKey?.let {
//                        nextKey(page.data, it)
//                    }
//                }
//            }
        }

        override suspend fun load(params: LoadParams<K>): LoadResult<K, V> {
            val currentKey = params.key ?: initialKey
            return try {
                val pagingResult = getItems(currentKey, params.loadSize)
                LoadResult.Page(
                    data = pagingResult.items,
                    prevKey = if (currentKey == initialKey) null else pagingResult.prevKey(),
                    nextKey = if (pagingResult.items.isEmpty()) null else pagingResult.nextKey()
                )
            } catch (exception: Exception) {
                return LoadResult.Error(exception)
            }
        }
    }

}