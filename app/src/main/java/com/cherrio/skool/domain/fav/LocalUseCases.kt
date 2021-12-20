package com.cherrio.skool.domain.fav

import com.cherrio.skool.data.mapper.toCourse
import com.cherrio.skool.data.mapper.toCourseEntity
import com.cherrio.skool.data.remote.model.UdemyCourse
import com.cherrio.skool.domain.fav.LocalRepository
import com.cherrio.skool.domain.request_entity.RequestCourses
import com.cherrio.skool.domain.response_entity.Course
import com.cherrio.skool.domain.response_entity.CoursePage
import com.cherrio.skool.domain.response_entity.Prefs
import com.cherrio.skool.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 *Created by Ayodele on 12/8/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

@Singleton
class LocalUseCases @Inject constructor(private val localRepository: LocalRepository
) {
    fun getCourses() = localRepository.getCourses().map {
           it.map{entity ->
               entity.toCourse()
           }
       }

    fun getPrefs(): Flow<Prefs>{
        return localRepository.getPrefs()
    }
    suspend fun setPref(prefs: Prefs){
        localRepository.savePrefs(prefs)
    }
    suspend fun addCourse(course: Course){
        localRepository.add(course.toCourseEntity())
    }
    suspend fun remove(course: Course){
        localRepository.remove(course.toCourseEntity())
    }
}