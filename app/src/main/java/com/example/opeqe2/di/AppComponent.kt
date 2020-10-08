package com.example.opeqe2.di

import com.example.opeqe2.view.main.MainFragment
import dagger.Component

@Component(modules = [ShapeModule::class])
interface AppComponent {
    fun inject(mainFragment: MainFragment)
}