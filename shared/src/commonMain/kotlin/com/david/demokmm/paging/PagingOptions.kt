package com.david.demokmm.paging

import kotlinx.coroutines.flow.Flow

public interface PagingOptions<Type : Any> {
    val pagingData: Flow<PagingData<Type>>
    public val hasNextPage: Boolean
    public fun loadNext()
}