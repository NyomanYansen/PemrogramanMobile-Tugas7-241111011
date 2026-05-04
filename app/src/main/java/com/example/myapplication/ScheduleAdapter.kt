package com.example.myapplication

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

sealed class ScheduleItem {
    data class Header(val day: String) : ScheduleItem()
    data class Content(val course: Course) : ScheduleItem()
}

class ScheduleAdapter(private var items: List<ScheduleItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_CONTENT = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ScheduleItem.Header -> TYPE_HEADER
            is ScheduleItem.Content -> TYPE_CONTENT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_HEADER) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_schedule_header, parent, false)
            HeaderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_course, parent, false)
            CourseViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if (holder is HeaderViewHolder && item is ScheduleItem.Header) {
            holder.tvHeader.text = item.day
        } else if (holder is CourseViewHolder && item is ScheduleItem.Content) {
            val course = item.course
            holder.tvCode.text = course.code
            holder.tvName.text = course.name
            holder.tvTime.text = course.time
            holder.tvRoom.text = course.room
            holder.tvLecturer.text = course.lecturer

            // Set dynamic color
            try {
                val color = Color.parseColor(course.color)
                holder.viewAccent.setBackgroundColor(color)
                
                val bgCode = holder.tvCode.background as GradientDrawable
                bgCode.setColor(color)
            } catch (e: Exception) {
                holder.viewAccent.setBackgroundColor(Color.BLUE)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateData(newItems: List<ScheduleItem>) {
        this.items = newItems
        notifyDataSetChanged()
    }

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvHeader: TextView = view.findViewById(R.id.tvHeaderDay)
    }

    class CourseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val viewAccent: View = view.findViewById(R.id.viewAccent)
        val tvCode: TextView = view.findViewById(R.id.tvCourseCode)
        val tvName: TextView = view.findViewById(R.id.tvCourseName)
        val tvTime: TextView = view.findViewById(R.id.tvCourseTime)
        val tvRoom: TextView = view.findViewById(R.id.tvCourseRoom)
        val tvLecturer: TextView = view.findViewById(R.id.tvCourseLecturer)
    }
}
