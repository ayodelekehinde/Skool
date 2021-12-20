package com.cherrio.skool.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 *Created by Ayodele on 12/8/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

data class UdemyCourse(@SerializedName("next") val next: String?,
                       @SerializedName("results") val results: List<CourseDTO>
)
data class CourseDTO(@SerializedName("id") val courseId: Long,
                     @SerializedName("title") val title: String,
                     @SerializedName("url") val link: String,
                     @SerializedName("price") val price: String,
                     @SerializedName("image_480x270") val image: String,
                     @SerializedName("headline") val headline: String,
                     @SerializedName("visible_instructors") val instructors: List<Instructor>
                     ){

    data class Instructor(@SerializedName("title") val title: String,
                          @SerializedName("image_100x100") val image: String
    )
}