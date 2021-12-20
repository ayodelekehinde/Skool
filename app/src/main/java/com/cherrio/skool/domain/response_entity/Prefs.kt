package com.cherrio.skool.domain.response_entity

/**
 *Created by Ayodele on 12/11/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

data class Prefs(
    val category: String = "Business",
    val price: String = "price-free",
    val learningLevel: String = "beginner",
    val ordering: String = "relevance",
    val isSet: Boolean = false
)
