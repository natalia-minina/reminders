package com.example.reminders.mvp.views

import moxy.MvpView
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType


interface NewReminderView : MvpView {

    @StateStrategyType(value = SkipStrategy::class)
    fun openNewReminderTime(text: String)
}