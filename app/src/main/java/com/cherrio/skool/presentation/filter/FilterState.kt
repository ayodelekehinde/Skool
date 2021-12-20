package com.cherrio.skool.presentation.filter

import com.cherrio.skool.domain.response_entity.Prefs
import com.cherrio.skool.presentation.pick_course.Category

/**
 *Created by Ayodele on 12/13/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

data class FilterState(val prefs: Prefs? = null,
                       val categories: MutableList<Category>? = null,
                       val prices: List<String>? = null,
                       val levels: List<String>? = null,
                       val orders: List<String>? = null

)
