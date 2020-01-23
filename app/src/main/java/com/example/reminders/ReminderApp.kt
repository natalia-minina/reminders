package com.example.reminders

import android.app.Application
import android.content.Context
import com.example.reminders.di.AppComponent
import com.example.reminders.di.CoreModule
import com.example.reminders.di.DaggerAppComponent


class ReminderApp : Application() {

    companion object {

        private var instance: ReminderApp? = null
        lateinit var graph: AppComponent

        fun getAppContext(): Context {
            return instance!!.getApplicationContext()
        }

        fun getInstance(): ReminderApp {
            return instance!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        graph = DaggerAppComponent.builder().coreModule(CoreModule()).build()

    }

}