package com.example.reminders.mvp.models

data class Reminder(val text: String, val date: Long, val id: String) {
    constructor() : this("", 0, "")
}