package com.david.demokmm.paging

/**
 * Copyright 2021, Kurt Renzo Acosta, All rights reserved.
 *
 * @author Kurt Renzo Acosta
 * @since 02/08/2021
 */
data class PagingResult<Key, Type>(
    val items: List<Type>,
    val currentKey: Key,
    val prevKey: () -> Key?,
    val nextKey: () -> Key?,
)