package com.david.demokmm.paging

import com.david.demokmm.utils.FlowWrapper
import kotlinx.coroutines.flow.Flow

public interface PagedData<Type : Any> {
    val pagingData: FlowWrapper<PagingData<Type>>
    public val hasNextPage: Boolean
    public fun loadNext()
}