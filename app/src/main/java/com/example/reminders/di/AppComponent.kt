package com.example.reminders.di

import com.example.reminders.mvp.presenters.MainPresenter
import com.example.reminders.mvp.presenters.NewReminderPresenter
import com.example.reminders.mvp.presenters.NewReminderTimePresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(CoreModule::class))
interface AppComponent {

    fun inject(mainPresenter: MainPresenter)
    fun inject(newReminderPresenter: NewReminderPresenter)
    fun inject(newReminderTimePresenter: NewReminderTimePresenter)
}