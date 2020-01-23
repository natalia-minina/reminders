package com.example.reminders.mvp.presenters

import android.text.TextUtils
import com.example.reminders.R
import com.example.reminders.ReminderApp
import com.example.reminders.mvp.models.MainModel
import com.example.reminders.mvp.views.MainView
import com.google.firebase.database.Query
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class MainPresenter : BasePresenter<MainView>() {
    @Inject
    lateinit var model: MainModel
    private var deleteReminderSubscription: Disposable? = null

    init {
        ReminderApp.graph.inject(this)
    }

    fun getQuery(): Query = model.getQuery()

    fun newReminder() {
        viewState.openNewReminder()
    }

    fun deleteReminder(id: String) {
        compositeRemove(deleteReminderSubscription)
        deleteReminderSubscription = model.deleteNote(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { it ->
                    if (!TextUtils.isEmpty(it.error)) {
                        viewState.showError(it.error!!)
                    }
                },
                { error -> viewState.showError(ReminderApp.getAppContext().getString(R.string.error)) },
                {}
            )

        compositeAdd(deleteReminderSubscription)
    }

}