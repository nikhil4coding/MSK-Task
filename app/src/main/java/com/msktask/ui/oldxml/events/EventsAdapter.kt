package com.msktask.ui.oldxml.events

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.msktask.R
import com.msktask.ui.model.MSKDataUI

class EventsAdapter(private val eventClickListener: OnEventClickListener) : RecyclerView.Adapter<EventsAdapter.ViewHolder>() {
    private var data = mutableListOf<MSKDataUI>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_recycle_item, parent, false)
        return ViewHolder(itemView)
    }

    fun setData(newData: List<MSKDataUI>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.idTextView.text = holder.itemView.context.getString(R.string.event_id, data[position].id)
        holder.descriptionTextView.text =  holder.itemView.context.getString(R.string.description, data[position].desc)

        holder.itemView.setOnClickListener {
            eventClickListener.onEventClick(
                data[position].id
            )
        }
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val idTextView: TextView = itemView.findViewById(R.id.item_id)
        val descriptionTextView: TextView = itemView.findViewById(R.id.item_description)
    }
}

interface OnEventClickListener {
    fun onEventClick(eventId: String)
}