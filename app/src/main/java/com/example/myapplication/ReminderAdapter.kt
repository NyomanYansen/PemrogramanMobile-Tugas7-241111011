package com.example.myapplication

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReminderAdapter(
    private var reminders: List<Reminder>,
    private val onEditClick: (Reminder) -> Unit,
    private val onDeleteClick: (Reminder) -> Unit
) : RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {

    class ReminderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val viewAccent: View = view.findViewById(R.id.viewAccent)
        val tvCourseCode: TextView = view.findViewById(R.id.tvCourseCode)
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvDescription: TextView = view.findViewById(R.id.tvDescription)
        val tvDeadline: TextView = view.findViewById(R.id.tvDeadline)
        val btnEdit: ImageView = view.findViewById(R.id.btnEdit)
        val btnDelete: ImageView = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reminder, parent, false)
        return ReminderViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val reminder = reminders[position]
        
        holder.tvCourseCode.text = reminder.courseCode
        holder.tvTitle.text = reminder.title
        holder.tvDescription.text = reminder.description
        holder.tvDeadline.text = reminder.deadline
        
        // Set accent color
        try {
            holder.viewAccent.setBackgroundColor(Color.parseColor(reminder.color))
            holder.tvCourseCode.setTextColor(Color.parseColor(reminder.color))
        } catch (e: Exception) {
            holder.viewAccent.setBackgroundColor(Color.BLUE)
        }

        holder.btnEdit.setOnClickListener { onEditClick(reminder) }
        holder.btnDelete.setOnClickListener { onDeleteClick(reminder) }
    }

    override fun getItemCount(): Int = reminders.size

    fun updateData(newReminders: List<Reminder>) {
        this.reminders = newReminders
        notifyDataSetChanged()
    }
}
