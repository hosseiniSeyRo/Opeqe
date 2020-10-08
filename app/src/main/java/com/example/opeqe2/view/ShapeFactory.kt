package com.example.opeqe2.view

import android.graphics.drawable.Drawable
import com.example.opeqe2.utils.CircleDrawable

interface ShapeFactory {
    fun getShape(): Drawable
}

class CircleFactory : ShapeFactory {
    override fun getShape(): Drawable {
        return CircleDrawable()
    }
}

