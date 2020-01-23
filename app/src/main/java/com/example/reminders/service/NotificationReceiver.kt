package com.example.reminders.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.reminders.Utils

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val intent1 = Intent(context, NotificationIntentService::class.java)
        val reminderText: String? = intent?.extras?.getString(Utils.INTENT_REMINDER_TEXT)
        Log.d("reminders", "reminderText=" + reminderText)
        intent1.putExtra(Utils.INTENT_REMINDER_TEXT, reminderText)
        context.startService(intent1)
    }
}