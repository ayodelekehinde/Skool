package com.cherrio.skool.presentation.pick_course

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cherrio.skool.domain.fav.LocalUseCases
import com.cherrio.skool.domain.response_entity.Prefs
import com.cherrio.skool.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *Created by Ayodele on 12/12/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

@HiltViewModel
class WelcomeViewModel @Inject constructor(private val localUseCases: LocalUseCases): ViewModel() {
    private val _state = MutableStateFlow(WelcomeState())
    val state = _state.asStateFlow()

    init {
        getCategory()
    }

    private fun getCategory(){
        _state.value = WelcomeState(categories = Constants.categories)
    }
    fun setCategory(category: Category){
        val prefs = Prefs().copy(category = category.cat, isSet = true)
        viewModelScope.launch {
            localUseCases.setPref(prefs)
            _state.value = WelcomeState(success = true)
        }
    }

}