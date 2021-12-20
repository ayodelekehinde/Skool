package com.cherrio.skool.utils

import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 *Created by Ayodele on 12/8/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

fun <T> Flow<T>.consume(coroutineScope: CoroutineScope, action: (T) -> Unit){
    coroutineScope.launch {
        collectLatest {
            action(it)
        }
    }
}
fun <T> StateFlow<T>.postTo(lifecycleOwner: LifecycleOwner, action: (T) -> Unit){
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
            collectLatest {
                action.invoke(it)
            }
        }
    }
}
fun <T> Flow<T>.consume(lifecycleOwner: LifecycleOwner, action: (T) -> Unit){
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
            collectLatest {
                action.invoke(it)
            }
        }
    }
}
suspend fun <T> Flow<T>.returnFlow(action: (T) -> Unit): Flow<T> {
    collectLatest {
        action(it)
    }
    return this
}