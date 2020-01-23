package com.example.reminders.ui.activities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.example.reminders.R
import com.example.reminders.ReminderApp
import com.example.reminders.Utils
import com.example.reminders.mvp.models.Reminder
import com.example.reminders.mvp.presenters.MainPresenter
import com.example.reminders.mvp.views.MainView
import com.example.reminders.ui.adapters.MainAdapter
import com.example.reminders.ui.views.RecyclerViewItemDecoration
import com.firebase.ui.database.FirebaseRecyclerOptions
import kotlinx.android.synthetic.main.activity_main.*
import moxy.presenter.InjectPresenter

class MainActivity : BaseActivity(), MainView, MainAdapter.OnListFragmentInteractionListener {

    @InjectPresenter
    lateinit var presenter: MainPresenter
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fab.setOnClickListener { view ->
            presenter.newReminder()
        }

        val layoutManager = LinearLayoutManager(ReminderApp.getAppContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        listView.layoutManager = layoutManager

        val postsQuery = presenter.getQuery()
        val options = FirebaseRecyclerOptions.Builder<Reminder>()
            .setQuery(postsQuery, Reminder::class.java)
            .build()
        adapter = MainAdapter(this, this, options)

        listView.setAdapter(adapter)

        listView.setEmptyView(emptyView)

        val space = resources.getDimensionPixelSize(R.dimen.app_item_offset)
        val bigSpace = resources.getDimensionPixelSize(R.dimen.app_item_big_offset)
        val itemDecoration = RecyclerViewItemDecoration(space, bigSpace)
        listView.addItemDecoration(itemDecoration)

        createNotificationChannel()

    }

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun getToolbarTitle(): String = getString(R.string.app_name)

    override fun onClickDeleteIcon(itemId: String) {
        presenter.deleteReminder(itemId)
    }

    override fun openNewReminder() {
        val intent = Intent(ReminderApp.getAppContext(), NewReminderActivity::class.java)
        startActivity(intent)
    }

    override fun isDisplayHomeAsUpEnabled(): Boolean = false

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun showError(text: String) {
        Toast.makeText(this, getString(R.string.error), Toast.LENGTH_LONG).show()
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.app_name)
            val descriptionText = getString(R.string.app_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(Utils.CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
