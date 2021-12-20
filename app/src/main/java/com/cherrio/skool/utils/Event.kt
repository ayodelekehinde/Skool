package com.cherrio.skool.utils

/**
 *Created by Ayodele on 12/11/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

class Event<T>(private val content: T) {
    private var handled = false

    fun getContent(): T?{
        return if (!handled){
            handled = true
            content
        }else{
            null
        }
    }
}