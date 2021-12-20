package com.cherrio.skool.data.repo

import android.util.Base64
import com.cherrio.skool.BuildConfig
import com.cherrio.skool.data.remote.UdemyApi
import com.cherrio.skool.data.remote.model.UdemyCourse
import com.cherrio.skool.data.remote.model.UdemyCurriculum
import com.cherrio.skool.domain.home.UdemyRepository
import com.cherrio.skool.domain.request_entity.RequestCourses
import com.cherrio.skool.domain.request_entity.RequestSearch
import javax.inject.Inject
import javax.inject.Singleton

/**
 *Created by Ayodele on 12/8/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

@Singleton
class UdemyRepoImpl @Inject constructor(private val udemyApi: UdemyApi): UdemyRepository {

    override suspend fun getCourses(requestCourses: RequestCourses): UdemyCourse {
        val map = mapOf(
            "category" to requestCourses.category,
            "price" to requestCourses.price,
            "instructional_level" to requestCourses.learningLevel,
            "ordering" to requestCourses.ordering,
            "page" to requestCourses.page,
            "page_size" to requestCourses.pageSize
        )
        return udemyApi.getCourses(getBasicKey(), map)
    }

    override suspend fun getCurriculum(page: String, id: Long): UdemyCurriculum {
       return udemyApi.getCurriculum(getBasicKey(),id, page)
    }

    override suspend fun search(requestSearch: RequestSearch): UdemyCourse {
        val map = mapOf(
            "search" to requestSearch.query,
            "page" to requestSearch.page,
            "page_size" to "20"
        )
        return udemyApi.getCourses(getBasicKey(), map)
    }


    private fun getBasicKey(): String{
        val key = BuildConfig.CLIENT_ID + ":" + BuildConfig.CLIENT_SECRET
        val token = Base64.encodeToString(key.encodeToByteArray(), Base64.NO_WRAP)
        return "Basic $token"
    }
}