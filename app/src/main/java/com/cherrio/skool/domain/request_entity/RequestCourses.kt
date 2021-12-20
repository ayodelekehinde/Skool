package com.cherrio.skool.domain.request_entity

/**
 *Created by Ayodele on 12/8/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

data class RequestCourses(val category: String,
                          val page: String,
                          val pageSize: String,
                          val price: String,
                          val learningLevel: String,
                          val ordering: String
)
