package com.david.demokmm.paging

import com.david.demokmm.utils.FlowWrapper
import kotlinx.coroutines.flow.Flow


public expect interface PagedData<Type : Any> {
    val pagingData: FlowWrapper<PagingData<Type>>
}