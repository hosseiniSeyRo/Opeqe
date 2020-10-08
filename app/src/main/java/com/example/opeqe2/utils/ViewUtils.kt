package com.example.opeqe2.utils

import android.content.Context
import android.util.DisplayMetrics


fun Int.dpToPixel(context: Context): Int {
    return this * (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
}