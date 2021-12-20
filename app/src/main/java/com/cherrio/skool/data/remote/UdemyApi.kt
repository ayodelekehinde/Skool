package com.cherrio.skool.data.remote

import com.cherrio.skool.data.remote.model.CourseDTO
import com.cherrio.skool.data.remote.model.CurriculumDTO
import com.cherrio.skool.data.remote.model.UdemyCourse
import com.cherrio.skool.data.remote.model.UdemyCurriculum
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 *Created by Ayodele on 12/8/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

interface UdemyApi {

    @GET("courses")
    suspend fun getCourses(@Header("Authorization") token: String, @QueryMap queries: Map<String, String>): UdemyCourse

    @GET("courses/{id}/public-curriculum-items")
    suspend fun getCurriculum(@Header("Authorization") token: String, @Path("id") id: Long, @Query("page") page: String): UdemyCurriculum

}