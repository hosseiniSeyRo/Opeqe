package com.example.opeqe2.utils

import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.Shape
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

class MyDrawable {

    private val imageWidth = 200
    private val imageHeight = 200

    private fun createShapeDrawable(shape: Shape): ShapeDrawable {
        val badge = ShapeDrawable(shape)
        badge.intrinsicWidth = imageWidth
        badge.intrinsicHeight = imageHeight
        badge.paint.color = Color.RED
        return badge
    }
}