package com.david.demokmm.paging.helpers

import androidx.paging.cachedIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import com.david.demokmm.paging.PagingData

/**
 * Copyright 2020, Plentina, All rights reserved.
 *
 * @author Kurt Renzo Acosta
 * @since 06/11/2020
 */
actual fun <T : Any> Flow<PagingData<T>>.cachedIn(scope: CoroutineScope) = cachedIn(scope)