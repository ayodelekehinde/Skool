package com.cherrio.skool.presentation.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cherrio.skool.domain.home.GetCoursesUseCase
import com.cherrio.skool.domain.response_entity.Course
import com.cherrio.skool.domain.response_entity.Prefs
import com.cherrio.skool.utils.Resource

/**
 *Created by Ayodele on 11/10/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

class CoursesSource(private val getCoursesUseCase: GetCoursesUseCase,
                    private val prefs: Prefs
                    ): PagingSource<Int, Course>() {

    override fun getRefreshKey(state: PagingState<Int, Course>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Course> {
        // Start refresh at page 1 if undefined.
        val nextPageNumber = params.key ?: 1
        return when(val response = getCoursesUseCase.invoke(nextPageNumber, prefs)){
            is Resource.Failed -> {
               LoadResult.Error(Throwable(response.error))
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