package com.cherrio.skool.presentation.saved

import com.cherrio.skool.domain.response_entity.Course

/**
 *Created by Ayodele on 12/11/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

data class FavouriteState(
    val loading: Boolean? = null,
    val courses: List<Course>? = null
)
