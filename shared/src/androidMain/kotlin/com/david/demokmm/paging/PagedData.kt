package com.david.demokmm.paging

import com.david.demokmm.utils.FlowWrapper
import kotlinx.coroutines.flow.emptyFlow

public actual interface PagedData<Type : Any>  {
    actual  val pagingData: FlowWrapper<PagingData<Type>> 
}