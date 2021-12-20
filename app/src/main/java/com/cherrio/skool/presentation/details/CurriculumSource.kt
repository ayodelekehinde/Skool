package com.cherrio.skool.presentation.details

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cherrio.skool.domain.home.GetCoursesUseCase
import com.cherrio.skool.domain.home.GetCurriculumUseCase
import com.cherrio.skool.domain.response_entity.ChapterAndLecture
import com.cherrio.skool.domain.response_entity.Course
import com.cherrio.skool.utils.Resource

/**
 *Created by Ayodele on 11/10/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

class CurriculumSource(private val getCurriculumUseCase: GetCurriculumUseCase,
                       private val id: Long

):
    PagingSource<Int, ChapterAndLecture>() {

    override fun getRefreshKey(state: PagingState<Int, ChapterAndLecture>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ChapterAndLecture> {
        // Start refresh at page 1 if undefined.
        val nextPageNumber = params.key ?: 1
        val response = getCurriculumUseCase.invoke(nextPageNumber, id)
       return when(response){
            is Resource.Failed -> {
               LoadResult.Error(Throwable(response.message))
            }
            is Resource.Success -> {
                val course = response.response
                val nextPage = if (response.response.next == null) null else  nextPageNumber + 1
                LoadResult.Page(
                    data = course.results,
                    prevKey = null, // Only paging forward.
                    nextKey = nextPage
                )
            }
       }

    }

}