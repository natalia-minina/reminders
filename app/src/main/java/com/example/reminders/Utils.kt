package com.example.reminders

import android.app.Activity
import android.provider.Settings
import android.text.format.DateUtils
import android.view.inputmethod.InputMethodManager
import java.text.SimpleDateFormat
import java.util.*


class Utils {

    companion object {
        const val INTENT_REMINDER_TEXT = "INTENT_REMINDER_TEXT"
        const val CHANNEL_ID = "REMINDER_CHANNEL_ID"

        private const val PATTERN_APP_DATE_TIME = "HH:mm, d MMM"
        private val PATTERN_APP_DATE_TIME_PARSER =
            SimpleDateFormat(
                PATTERN_APP_DATE_TIME, Locale("ru")
            )
        private const val PATTERN_APP_TIME = "HH:mm"
        private val PATTERN_APP_TIME_PARSER =
            SimpleDateFormat(
                PATTERN_APP_TIME, Locale("ru")
            )
        private const val PATTERN_APP_DATE = "d MMM"
        val PATTERN_APP_DATE_PARSER =
            SimpleDateFormat(
                PATTERN_APP_DATE, Locale("ru")
            )

        fun formatDateTime(time: Long): String {
            if (time == 0L) {
                return ""
            }
            try {
                PATTERN_APP_DATE_TIME_PARSER.timeZone = TimeZone.getDefault()
                return PATTERN_APP_DATE_TIME_PARSER.format(time)
            } catch (e: Exception) {
            }
            return ""
        }

        fun formatTime(time: Long): String {
            if (time == 0L) {
                return ""
            }
            try {
                PATTERN_APP_TIME_PARSER.timeZone = TimeZone.getDefault()
                return PATTERN_APP_TIME_PARSER.format(time)
            } catch (e: Exception) {
            }
            return ""
        }

        fun formatDate(time: Long): String {
            if (time == 0L) {
                return ""
            }
            try {
                if (DateUtils.isToday(time)) {
                    return ReminderApp.getAppContext().getString(R.string.today);
                }
                PATTERN_APP_DATE_PARSER.timeZone = TimeZone.getDefault()
                return PATTERN_APP_DATE_PARSER.format(time)
            } catch (e: Exception) {
            }
            return ""
        }

        fun getCurrentTime(): Long {
            val calendar = Calendar.getInstance()
            return calendar.timeInMillis
        }

        fun hideKeyboard(act: Activity?) {
            if (act != null && act.currentFocus != null) {
                val inputMethodManager =
                    act.getSystemService(
                        Activity.INPUT_METHOD_SERVICE
                    ) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(
                    act.currentFocus!!.windowToken,
                    0
                )
            }
        }

        fun getDataBaseDeviceKey(): String {
            return Settings.Secure.getString(
                ReminderApp.getAppContext().getContentResolver(),
                Settings.Secure.ANDROID_ID
            )
        }

        fun createID(): Int {
            val now = Date()
            return SimpleDateFormat("HHmmssSS", Locale.US).format(now).toInt()
        }
    }


}