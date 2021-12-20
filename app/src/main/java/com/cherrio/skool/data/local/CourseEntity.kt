package com.cherrio.skool.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cherrio.skool.domain.response_entity.Course

/**
 *Created by Ayodele on 12/11/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */
@Entity
data class CourseEntity(
    @PrimaryKey
    @ColumnInfo(name = "course_id") val courseId: Long,
    @ColumnInfo(name = "course_title")val title: String,
    @ColumnInfo(name = "course_link") val link: String,
    @ColumnInfo(name = "course_price") val price: String,
    @ColumnInfo(name = "course_image") val image: String,
    @ColumnInfo(name = "course_headline")val headline: String,
    @ColumnInfo(name = "course_instructor")val instructor: String,
    @ColumnInfo(name = "instructor_image")val instructorImage: String
)

