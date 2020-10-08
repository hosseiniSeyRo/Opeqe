package com.example.opeqe2

import android.app.Application
import com.example.opeqe2.di.AppComponent
import com.example.opeqe2.di.DaggerAppComponent

class MyApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.create()
    }
}