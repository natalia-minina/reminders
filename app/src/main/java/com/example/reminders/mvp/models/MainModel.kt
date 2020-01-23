package com.example.reminders.mvp.models

import com.example.reminders.R
import com.example.reminders.ReminderApp
import com.example.reminders.Utils
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import io.reactivex.Observable
import io.reactivex.ObservableEmitter

class MainModel {
    private val devicesDBKey = "devices"
    private val remindersDBKey = "reminders"

    private var database: DatabaseReference

    init {
        database = FirebaseDatabase.getInstance().reference.child(devicesDBKey)
            .child(Utils.getDataBaseDeviceKey())
    }

    fun getQuery(): Query {
        return database.child(remindersDBKey)
    }

    fun createNote(text: String, date: Long): Observable<Response> {
        return Observable.create { emitter: ObservableEmitter<Response> ->
            var response: Response? = null

            val key = database.child(remindersDBKey).push().key
            if (key == null) {
                response = Response(ReminderApp.getAppContext().getString(R.string.error))
            } else {
                val reminder = Reminder(text, date, key)
                database.child(remindersDBKey).child(key).setValue(reminder)

                response = Response(null)
            }
            emitter.onNext(response)
            emitter.onComplete()
        }
    }

    fun deleteNote(id: String): Observable<Response> {
        return Observable.create { emitter: ObservableEmitter<Response> ->
            database.child(remindersDBKey).child(id).setValue(null)
            emitter.onNext(Response(null))
            emitter.onComplete()
        }
    }

}