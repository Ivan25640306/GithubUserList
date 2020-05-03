package com.ivan.android.githubuserlist.util

import android.content.Context
import android.util.TypedValue

fun Context.dpToPx(dp: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, this.resources.displayMetrics)
        .toInt()
}