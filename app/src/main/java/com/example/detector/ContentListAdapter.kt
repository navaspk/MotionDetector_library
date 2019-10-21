package com.example.detector

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContentListAdapter(var mContext : Context) : RecyclerView.Adapter<ContentListAdapter.EventViewHolder>() {

    private var mInflater: LayoutInflater? = null
    private var mEvents: List<String>? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        if (mInflater == null)
            mInflater = LayoutInflater.from(mContext)
        val itemView = mInflater?.inflate(R.layout.recyclerview_item, parent, false)
        return EventViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        if (mEvents != null) {
            val current = mEvents!![position]
            holder.contentItemView.setText(current)
        }
    }

    override fun getItemCount(): Int {
        return if (mEvents != null)
            mEvents!!.size
        else
            0
    }

    internal fun setEvents(events: List<String>) {
        mEvents = events
        notifyDataSetChanged()
    }

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val contentItemView: TextView
        init {
            contentItemView = itemView.findViewById(R.id.textView)
        }
    }
}