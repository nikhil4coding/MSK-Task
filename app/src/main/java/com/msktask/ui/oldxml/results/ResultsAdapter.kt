package com.msktask.ui.oldxml.results

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.msktask.R
import com.msktask.ui.model.ResultsUI

class ResultsAdapter : RecyclerView.Adapter<ResultsAdapter.ViewHolder>() {
    var onResultClick: ((ResultsUI) -> Unit)? = null
    private var data = mutableListOf<ResultsUI>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_recycle_item, parent, false)
        return ViewHolder(itemView)
    }

    fun setData(newData: List<ResultsUI>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.idTextView.text = holder.itemView.context.getString(R.string.result_id, data[position].id)
        holder.descriptionTextView.text = holder.itemView.context.getString(R.string.description, data[position].desc)
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val idTextView: TextView = itemView.findViewById(R.id.item_id)
        val descriptionTextView: TextView = itemView.findViewById(R.id.item_description)

        override fun onClick(view: View) {
            onResultClick?.invoke(data[bindingAdapterPosition])
        }
    }
}