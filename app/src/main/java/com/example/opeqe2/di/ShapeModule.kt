package com.example.opeqe2.di

import com.example.opeqe2.view.CircleFactory
import com.example.opeqe2.view.ShapeFactory
import dagger.Module
import dagger.Provides

@Module
class ShapeModule {

    @Provides
    fun shapeFactoryProvider(): ShapeFactory {
        return CircleFactory()
    }
}