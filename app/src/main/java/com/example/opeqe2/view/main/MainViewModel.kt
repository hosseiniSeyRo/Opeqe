package com.example.opeqe2.view.main

import androidx.lifecycle.ViewModel
import com.example.opeqe2.utils.SingleLiveData

class MainViewModel : ViewModel() {

    private val _drawShape = SingleLiveData<Unit>()
    val drawShape = _drawShape
    private val _alignHorizontally = SingleLiveData<Unit>()
    val alignHorizontally = _alignHorizontally
    private val _alignVertically = SingleLiveData<Unit>()
    val alignVertically = _alignVertically

    fun onDrawShapeClicked() {
        drawShape.value = Unit
    }

    fun onAlignHorizontallyClicked() {
        alignHorizontally.value = Unit
    }

    fun onAlignVerticallyClicked() {
        alignVertically.value = Unit
    }
}