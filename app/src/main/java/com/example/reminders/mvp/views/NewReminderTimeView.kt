package com.example.reminders.mvp.views

import moxy.MvpView
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType


interface NewReminderTimeView : MvpView {

    @StateStrategyType(value = SkipStrategy::class)
    fun showError(text: String)

    @StateStrategyType(value = SkipStrategy::class)
    fun success(text: String, date: Long)
}