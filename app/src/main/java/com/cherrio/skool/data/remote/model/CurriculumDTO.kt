package com.cherrio.skool.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 *Created by Ayodele on 12/8/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

data class UdemyCurriculum(@SerializedName("next") val next: String?,
                       @SerializedName("results") val results: List<CurriculumDTO>
)
data class CurriculumDTO(@SerializedName("id") val courseId: Long,
                         @SerializedName("title") val title: String,
                         @SerializedName("_class") val type: String,
                         @SerializedName("asset") val assetDTO: AssetDTO?,
                         ){
    data class AssetDTO(@SerializedName("asset_type") val assetType: String)
}
