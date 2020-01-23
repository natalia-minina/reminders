package com.example.reminders.ui.activities

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.reminders.R
import com.example.reminders.Utils
import com.example.reminders.mvp.presenters.NewReminderTimePresenter
import com.example.reminders.mvp.views.NewReminderTimeView
import com.example.reminders.service.NotificationReceiver
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker
import kotlinx.android.synthetic.main.activity_new_reminder_time.*
import moxy.presenter.InjectPresenter
import java.util.*


class NewReminderTimeActivity : BaseActivity(), NewReminderTimeView,
    SingleDateAndTimePicker.OnDateChangedListener {

    @InjectPresenter
    lateinit var presenter: NewReminderTimePresenter

    private var reminderText: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        reminderText = intent.extras?.getString(Utils.INTENT_REMINDER_TEXT)

        singleDateAndTimePicker.setIsAmPm(false)
        singleDateAndTimePicker.setMustBeOnFuture(true)
        singleDateAndTimePicker.setStepMinutes(1)
        singleDateAndTimePicker.setCustomLocale(Locale("ru"))
        singleDateAndTimePicker.setDayFormatter(Utils.PATTERN_APP_DATE_PARSER)
        singleDateAndTimePicker.addOnDateChangedListener(this)

        val calendar = Calendar.getInstance()
        val minutes = calendar[Calendar.MINUTE]
        calendar.set(Calendar.MINUTE, minutes + 1)
        singleDateAndTimePicker.setDefaultDate(calendar.time)
        remindTV.text = remindText(calendar.timeInMillis)

        remindTV.setOnClickListener { view ->
            presenter.onClickRemind(
                if (reminderText == null) "" else reminderText!!,
                singleDateAndTimePicker.date.time
            )
        }

    }

    override fun getLayoutId(): Int = R.layout.activity_new_reminder_time

    override fun getToolbarTitle(): String = getString(R.string.new_reminder)

    override fun onDateChanged(displayed: String?, date: Date?) {
        remindTV.text = remindText(singleDateAndTimePicker.date.time)
    }

    private fun remindText(time: Long): String {
        return resources.getString(R.string.remind) + " " +
                Utils.formatDate(time) + " " +
                resources.getString(R.string.in_) + " " +
                Utils.formatTime(time)
    }

    override fun showError(text: String) {
        Toast.makeText(this, getString(R.string.error), Toast.LENGTH_LONG).show()
    }

    override fun success(text: String, date: Long) {
        val notifyIntent = Intent(this, NotificationReceiver::class.java)
        notifyIntent.putExtra(Utils.INTENT_REMINDER_TEXT, text)

        val pendingIntent = PendingIntent.getBroadcast(
            this,
            Utils.createID(),
            notifyIntent,
            0
        )
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC_WAKEUP, date, pendingIntent)

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        startActivity(intent)
    }
}
