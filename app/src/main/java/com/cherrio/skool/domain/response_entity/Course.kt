package com.cherrio.skool.domain.response_entity


import java.io.Serializable

/**
 *Created by Ayodele on 12/8/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

data class CoursePage(val next: String?,
                      val results: List<Course>)
data class Course(val courseId: Long,
                  val title: String,
                  val link: String,
                  val price: String,
                  val image: String,
                  val headline: String,
                  val instructor: String,
                  val instructorImage: String,
                  val isFav: Boolean,
                  ): Serializable
