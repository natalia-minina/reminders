package com.example.reminders.mvp.presenters

import android.text.TextUtils
import com.example.reminders.R
import com.example.reminders.ReminderApp
import com.example.reminders.mvp.models.MainModel
import com.example.reminders.mvp.views.NewReminderTimeView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import moxy.InjectViewState
import java.util.*
import javax.inject.Inject


@InjectViewState
class NewReminderTimePresenter : BasePresenter<NewReminderTimeView>() {
    @Inject
    lateinit var model: MainModel
    private var newReminderSubscription: Disposable? = null

    init {
        ReminderApp.graph.inject(this)
    }


    fun onClickRemind(text: String, date: Long) {
        compositeRemove(newReminderSubscription)
        val calendar = Calendar.getInstance()
        val minutes = calendar[Calendar.MINUTE]
        calendar.set(Calendar.MINUTE, minutes + 1);
        var dateEnd: Long = date
        if (date < calendar.time.time) {
            dateEnd = calendar.time.time;
        }
        calendar.timeInMillis = dateEnd;
        calendar.set(Calendar.SECOND, 0);
        dateEnd = calendar.time.time;
        newReminderSubscription = model.createNote(text, dateEnd)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { it ->
                    if (TextUtils.isEmpty(it.error)) {
                        viewState.success(text, dateEnd);
                    } else {
                        viewState.showError(it.error!!)
                    }
                },
                { error -> viewState.showError(ReminderApp.getAppContext().getString(R.string.error)) },
                {}
            )

        compositeAdd(newReminderSubscription)
    }

}