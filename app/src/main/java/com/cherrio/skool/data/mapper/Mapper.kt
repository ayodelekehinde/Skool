package com.cherrio.skool.data.mapper

import com.cherrio.skool.data.local.CourseEntity
import com.cherrio.skool.domain.response_entity.Course

/**
 *Created by Ayodele on 12/11/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

fun Course.toCourseEntity() = CourseEntity(
    courseId, title, link, price, image, headline, instructor, instructorImage
)
fun CourseEntity.toCourse() = Course(
    courseId, title, link, price, image, headline, instructor, instructorImage, true
)