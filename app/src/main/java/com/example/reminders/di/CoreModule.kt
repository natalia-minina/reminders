package com.example.reminders.di

import com.example.reminders.mvp.models.MainModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CoreModule {

    @Provides
    @Singleton
    fun provideMainModel(): MainModel = MainModel()
}