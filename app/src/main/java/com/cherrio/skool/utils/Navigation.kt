package com.cherrio.skool.utils

import androidx.annotation.IdRes
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

/**
 *Created by Ayodele on 12/11/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

fun Fragment.findNavControllerById(@IdRes id: Int): NavController {
    var parent = parentFragment
    while (parent != null) {
        if (parent is NavHostFragment && parent.id == id) {
            return parent.navController
        }
        parent = parent.parentFragment
    }
    throw RuntimeException("NavController with specified id not found")
}
inline fun NestedScrollView.scrollingUpOrDown(crossinline action: (Boolean) -> Unit){
    val listener = NestedScrollView.OnScrollChangeListener {
            _, _, scrollY, _, oldScrollY ->

        if (scrollY > oldScrollY) {
            //Scroll DOWN
            action(true)
        }
        if (scrollY == 0) {
            action(false)
        }


    }
    setOnScrollChangeListener(listener)
}