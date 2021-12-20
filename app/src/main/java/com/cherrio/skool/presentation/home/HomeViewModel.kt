package com.cherrio.skool.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cherrio.skool.domain.fav.LocalUseCases
import com.cherrio.skool.domain.home.GetCoursesUseCase
import com.cherrio.skool.domain.response_entity.Course
import com.cherrio.skool.domain.response_entity.Prefs
import com.cherrio.skool.utils.returnFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *Created by Ayodele on 12/8/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCoursesUseCase: GetCoursesUseCase,
    private val localUseCases: LocalUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        getPrefsData()
    }

    private fun getPrefsData() {
        viewModelScope.launch(Dispatchers.IO) {
            localUseCases.getPrefs().collectLatest {
                _state.value = HomeState(prefs = it)
            }
        }
    }

    fun getPaginatedData(prefs: Prefs): Flow<PagingData<Course>> {
        return Pager(PagingConfig(pageSize = 20)) {
                CoursesSource(getCoursesUseCase, prefs)
            }.flow.cachedIn(viewModelScope)
    }
}