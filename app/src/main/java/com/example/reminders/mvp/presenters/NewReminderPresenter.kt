package com.example.reminders.mvp.presenters

import com.example.reminders.mvp.views.NewReminderView
import moxy.InjectViewState

@InjectViewState
class NewReminderPresenter : BasePresenter<NewReminderView>() {

    fun onClickNext(text: String) {
        viewState.openNewReminderTime(text)
    }

}