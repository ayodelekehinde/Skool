package com.cherrio.skool.domain.fav

import com.cherrio.skool.data.local.CourseEntity
import com.cherrio.skool.domain.response_entity.Course
import com.cherrio.skool.domain.response_entity.Prefs
import kotlinx.coroutines.flow.Flow

/**
 *Created by Ayodele on 12/11/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

interface LocalRepository {

   suspend fun add(course: CourseEntity)

   suspend fun remove(course: CourseEntity)

   fun getCourses(): Flow<List<CourseEntity>>

   suspend fun getCourse(id: Long): List<CourseEntity>

   fun getPrefs(): Flow<Prefs>

   suspend fun savePrefs(prefs: Prefs)
}