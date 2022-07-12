package com.david.demokmm.paging

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow

/**
 * Copyright 2020, Kurt Renzo Acosta, All rights reserved.
 *
 * @author Kurt Renzo Acosta
 * @since 06/11/2020
 */

@FlowPreview
@ExperimentalCoroutinesApi
expect class Pager<Key : Any, Type : Any>(
    clientScope: CoroutineScope,
    config: PagingConfig,
    initialKey: Key,
    getItems: suspend (Key, Int) -> PagingResult<Key, Type>
) : PagingOptions<Type> {
}
