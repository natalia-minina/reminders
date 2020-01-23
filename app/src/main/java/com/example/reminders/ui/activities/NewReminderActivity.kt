package com.example.reminders.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.reminders.R
import com.example.reminders.ReminderApp
import com.example.reminders.Utils
import com.example.reminders.mvp.presenters.NewReminderPresenter
import com.example.reminders.mvp.views.NewReminderView
import kotlinx.android.synthetic.main.activity_new_reminder.*
import moxy.presenter.InjectPresenter

class NewReminderActivity : BaseActivity(), NewReminderView {

    @InjectPresenter
    lateinit var presenter: NewReminderPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        nextTV.setOnClickListener { view ->
            presenter.onClickNext(textET.text.toString())
        }

    }

    override fun getLayoutId(): Int = R.layout.activity_new_reminder

    override fun getToolbarTitle(): String = getString(R.string.new_reminder)

    override fun openNewReminderTime(text: String) {
        if (TextUtils.isEmpty(text)) {
            Toast.makeText(this, getString(R.string.enter_text), Toast.LENGTH_LONG).show()
        } else {
            Utils.hideKeyboard(this)
            val intent = Intent(ReminderApp.getAppContext(), NewReminderTimeActivity::class.java)
            intent.putExtra(Utils.INTENT_REMINDER_TEXT, text)
            startActivity(intent)
        }
    }

}
