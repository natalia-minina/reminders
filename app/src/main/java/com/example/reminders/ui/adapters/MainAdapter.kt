package com.example.reminders.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.reminders.R
import com.example.reminders.Utils
import com.example.reminders.mvp.models.Reminder
import com.example.reminders.ui.adapters.MainAdapter.ReminderViewHolder
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions


class MainAdapter constructor(
    _context: Context,
    _listener: OnListFragmentInteractionListener,
    options: FirebaseRecyclerOptions<Reminder>
) :
    FirebaseRecyclerAdapter<Reminder, ReminderViewHolder>(options) {
    private val listener: OnListFragmentInteractionListener = _listener
    private val context: Context = _context

    interface OnListFragmentInteractionListener {
        fun onClickDeleteIcon(itemId: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ReminderViewHolder(
        LayoutInflater.from(context).inflate
            (R.layout.item_reminder, parent, false)
    )

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int, model: Reminder) {
        holder.bind(model)
    }

    inner class ReminderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textTV = view.findViewById<TextView>(R.id.textTV)
        private val dateTV = view.findViewById<TextView>(R.id.dateTV)
        private val closeIV = view.findViewById<ImageView>(R.id.closeIV)

        fun bind(item: Reminder) {
            textTV.text = item.text
            dateTV.text = Utils.formatDateTime(item.date)
            closeIV.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION)
                    listener.onClickDeleteIcon(item.id)
            }
        }
    }

}