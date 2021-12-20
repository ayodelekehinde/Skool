package com.cherrio.skool.utils

import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar

/**
 *Created by Ayodele on 12/10/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

fun ImageView.loadImage(uri: String){
    Glide.with(this).load(uri)
        .thumbnail(0.5f)
        .into(this)
}
fun ImageView.loadImage(view: View, url: String) {
    view.isVisible = true
    Glide.with(this).load(url).listener(object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean {
            view.isVisible = false
            return false
        }

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            view.isVisible = false
            return false
        }
    }).into(this)
}

fun snack(view: View, message: String){
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
}