package com.cherrio.skool.presentation.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cherrio.skool.domain.fav.LocalUseCases
import com.cherrio.skool.utils.consume
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 *Created by Ayodele on 12/11/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

@HiltViewModel
class FavouritesViewModel @Inject constructor(private val localUseCases: LocalUseCases) : ViewModel() {
    private val _state = MutableStateFlow(FavouriteState())
    val state = _state.asStateFlow()
    init {
        getCourses()
    }
    private fun getCourses(){
        _state.value = FavouriteState(loading = true)
        localUseCases.getCourses().consume(viewModelScope){
            _state.value = FavouriteState(loading = false, courses = it)
        }
    }

}