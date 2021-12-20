package com.cherrio.skool.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.cherrio.skool.domain.fav.LocalUseCases
import com.cherrio.skool.domain.home.GetCurriculumUseCase
import com.cherrio.skool.domain.response_entity.ChapterAndLecture
import com.cherrio.skool.domain.response_entity.Course
import com.cherrio.skool.presentation.home.CoursesSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *Created by Ayodele on 12/10/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

@HiltViewModel
class CourseDetailViewModel @Inject constructor(private val getCurriculumUseCase: GetCurriculumUseCase,
                                                private val localUseCases: LocalUseCases
) : ViewModel() {


    fun getPagedData(id: Long): Flow<PagingData<ChapterAndLecture>> {
        return Pager(PagingConfig(pageSize = 20)){
            CurriculumSource(getCurriculumUseCase, id)
        }.flow.cachedIn(viewModelScope)
    }
    fun addOrRemoveCourse(isAdd: Boolean, course: Course){
        viewModelScope.launch(Dispatchers.IO) {
            if (isAdd) {
                localUseCases.addCourse(course)
            }else{
                localUseCases.remove(course)
            }
        }
    }
}