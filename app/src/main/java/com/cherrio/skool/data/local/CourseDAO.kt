package com.cherrio.skool.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 *Created by Ayodele on 12/11/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */
@Dao
interface CourseDAO {

    @Query("SELECT * From courseentity")
    fun getCourses(): Flow<List<CourseEntity>>

    @Insert
    suspend fun addCourse(courseEntity: CourseEntity)

    @Query("SELECT * From courseentity WHERE course_id=:id")
    suspend fun getCourse(id: Long): List<CourseEntity>

    @Delete
    suspend fun removeCourse(courseEntity: CourseEntity)

}