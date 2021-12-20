package com.cherrio.skool.data.repo

import com.cherrio.skool.data.local.CourseDAO
import com.cherrio.skool.data.local.CourseEntity
import com.cherrio.skool.data.local.DataStoreManager
import com.cherrio.skool.data.mapper.toCourseEntity
import com.cherrio.skool.domain.fav.LocalRepository
import com.cherrio.skool.domain.response_entity.Course
import com.cherrio.skool.domain.response_entity.Prefs
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 *Created by Ayodele on 12/11/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

@Singleton
class LocalRepoImpl @Inject constructor(private val courseDAO: CourseDAO,
                                        private val dataStoreManager: DataStoreManager): LocalRepository {

    override suspend fun add(course: CourseEntity) {
      courseDAO.addCourse(course)
    }

    override suspend fun remove(course: CourseEntity) {
        courseDAO.removeCourse(course)
    }

    override fun getCourses(): Flow<List<CourseEntity>> {
        return courseDAO.getCourses()
    }

    override suspend fun getCourse(id: Long): List<CourseEntity> {
        return courseDAO.getCourse(id)
    }

    override fun getPrefs(): Flow<Prefs> {
        return dataStoreManager.getPref
    }

    override suspend fun savePrefs(prefs: Prefs) {
        dataStoreManager.setPref(prefs)
    }
}