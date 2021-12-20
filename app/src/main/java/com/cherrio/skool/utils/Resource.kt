package com.cherrio.skool.utils

/**
 *Created by Ayodele on 12/8/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

sealed class Resource<T>(val data: T? = null, val message: String? = null){

    //data class Loading<T>(val response: T? = null): Resource<T>(data = response)
    data class Success<T>(val response: T): Resource<T>(data = response)
    data class Failed<T>(val error: String?): Resource<T>(message = error)
}
