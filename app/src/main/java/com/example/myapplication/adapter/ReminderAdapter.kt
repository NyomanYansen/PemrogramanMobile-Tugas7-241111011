package com.example.myapplication.adapter

import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.Reminder

class ReminderAdapter(private var list: List<Reminder>) : 
    RecyclerView.Adapter<ReminderAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val title: TextView = v.findViewById(R.id.tvTitle)
        val desc: TextView = v.findViewById(R.id.tvDescription)
        val dateTime: TextView = v.findViewById(R.id.tvDateTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_reminder, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.title.text = item.title
        holder.desc.text = item.description
        holder.dateTime.text = "${item.date} - ${item.remindTime}"
    }

    override fun getItemCount(): Int = list.size

    fun updateData(newList: List<Reminder>) {
        this.list = newList
        notifyDataSetChanged()
    }
}
