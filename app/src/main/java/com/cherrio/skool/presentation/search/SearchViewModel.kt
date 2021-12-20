package com.cherrio.skool.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cherrio.skool.domain.home.SearchUseCase
import com.cherrio.skool.domain.response_entity.Course
import com.cherrio.skool.domain.response_entity.Prefs
import com.cherrio.skool.presentation.home.CoursesSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 *Created by Ayodele on 12/14/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchUseCase: SearchUseCase) : ViewModel() {

    fun getPaginatedData(query: String): Flow<PagingData<Course>> {
        return Pager(PagingConfig(pageSize = 20)) {
            SearchCoursesSource(searchUseCase, query)
        }.flow.cachedIn(viewModelScope)
    }
}