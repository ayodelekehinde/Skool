package com.cherrio.skool.presentation.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cherrio.skool.domain.fav.LocalUseCases
import com.cherrio.skool.domain.response_entity.Prefs
import com.cherrio.skool.presentation.pick_course.Category
import com.cherrio.skool.utils.Constants
import com.cherrio.skool.utils.consume
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *Created by Ayodele on 12/13/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

@HiltViewModel
class FilterViewModel @Inject constructor(private val localUseCases: LocalUseCases): ViewModel() {
    private val _state = MutableStateFlow(FilterState())
    val state = _state.asStateFlow()

    init {
        getPrefs()
    }
    private fun getPrefs(){
        localUseCases.getPrefs().consume(viewModelScope){
            _state.value = FilterState(prefs = it,
                categories = Constants.categories.setChecked(it.category),
                prices = Constants.prices,
                orders = Constants.orders,
                levels = Constants.levels
            )
        }
    }

    fun setPrefs(prefs: Prefs){
        viewModelScope.launch {
            localUseCases.setPref(prefs)
        }
    }

    private fun MutableList<Category>.setChecked(cat: String): MutableList<Category>{
        val catlist = mutableListOf<Category>()
        forEach {
            if (cat == it.cat){
                catlist.add(Category(it.cat, true))
            }else{
                catlist.add(it)
            }
        }
        return catlist
    }
}