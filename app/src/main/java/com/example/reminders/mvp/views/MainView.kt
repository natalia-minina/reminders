package com.example.reminders.mvp.views

import moxy.MvpView
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType


interface MainView : MvpView {

    @StateStrategyType(value = SkipStrategy::class)
    fun openNewReminder()

    @StateStrategyType(value = SkipStrategy::class)
    fun showError(text: String)
}