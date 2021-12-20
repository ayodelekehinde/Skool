package com.cherrio.skool.presentation.home

import androidx.paging.PagingData
import com.cherrio.skool.domain.response_entity.Course
import com.cherrio.skool.domain.response_entity.Prefs

/**
 *Created by Ayodele on 12/11/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

data class HomeState(val prefs: Prefs? = null)
