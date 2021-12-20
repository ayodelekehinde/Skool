package com.cherrio.skool.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 *Created by Ayodele on 12/14/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(MainState())
    val mainState = _state.asStateFlow()

    fun setQuery(query: String){
        _state.value = MainState(searchQuery = query)
    }
}