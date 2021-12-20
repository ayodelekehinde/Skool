package com.cherrio.skool.domain.home

import com.cherrio.skool.data.remote.model.CourseDTO
import com.cherrio.skool.data.remote.model.UdemyCourse
import com.cherrio.skool.data.remote.model.UdemyCurriculum
import com.cherrio.skool.domain.request_entity.RequestCourses
import com.cherrio.skool.domain.request_entity.RequestSearch

/**
 *Created by Ayodele on 12/8/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

interface UdemyRepository {
    suspend fun getCourses(requestCourses: RequestCourses): UdemyCourse
    suspend fun getCurriculum(page: String, id: Long): UdemyCurriculum
    suspend fun search(requestSearch: RequestSearch): UdemyCourse

}