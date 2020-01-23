package com.example.reminders.service

import android.app.IntentService
import android.app.PendingIntent
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.util.Log
import com.example.reminders.R
import com.example.reminders.Utils
import com.example.reminders.ui.activities.MainActivity


class NotificationIntentService : IntentService("NotificationIntentService") {
    override fun onHandleIntent(intent: Intent?) {

        val reminderText: String? = intent?.extras?.getString(Utils.INTENT_REMINDER_TEXT)
        Log.d("reminders", "onHandleIntent =" + reminderText)
        val openIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(this, Utils.createID(), openIntent, 0)

        var builder = NotificationCompat.Builder(this, Utils.CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentTitle(getString(R.string.reminder))
            .setContentText(reminderText)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        NotificationManagerCompat.from(this).notify(Utils.createID(), builder.build())

    }
}