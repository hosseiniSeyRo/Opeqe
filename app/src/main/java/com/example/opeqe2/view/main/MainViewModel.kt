package com.example.opeqe2.view.main

import androidx.lifecycle.ViewModel
import com.example.opeqe2.utils.SingleLiveData

class MainViewModel : ViewModel() {

    val drawShape = SingleLiveData<Boolean>()
    val alignHorizontally = SingleLiveData<Boolean>()

    init {
        drawShape.value = false
    }

    fun drawShape() {
        drawShape.value = true
    }

    fun alignHorizontally() {
        alignHorizontally.value = true
    }
}