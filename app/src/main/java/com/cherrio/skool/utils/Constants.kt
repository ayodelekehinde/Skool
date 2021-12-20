package com.cherrio.skool.utils

import com.cherrio.skool.presentation.pick_course.Category

/**
 *Created by Ayodele on 12/8/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

object Constants {
    const val BASE_URL = "https://www.udemy.com/api-2.0/"
    val categories = mutableListOf(
        Category("Business"),
        Category("Finance & Accounting"),
        Category("Design"),
        Category("Development"),
        Category("Office Productivity"),
        Category("Photography & Video"),
        Category("Health & Fitness"),
        Category("IT & Software"),
        Category("Lifestyle"),
        Category("Personal Development"),
        Category("Marketing"),
        Category("Music"),
        Category("Teaching & Academics"),
        Category("Udemy Free Resource Center"),
        Category("Vodafone")
    )
    val prices = listOf("price-free","price-paid")
    val levels = listOf("beginner","intermediate","expert")
    val orders = listOf("relevance",
        "most-reviewed",
        "highest-rated",
        "newest",
        "price-low-to-high",
        "price-high-to-low"
    )
}