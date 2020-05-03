package com.ivan.android.githubuserlist.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.ivan.android.githubuserlist.R

fun ImageView.loadUrlWithCircleCrop(url: String?, size: Int = -1) {
    val glideBuilder =
        Glide.with(this).load(url).placeholder(R.drawable.ic_mood_green)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .thumbnail(0.3f)

    if (size != -1) {
        glideBuilder.override(size)
    }

    glideBuilder.into(this)
}