package com.example.myapplication.repository

import com.example.myapplication.Reminder
import com.example.myapplication.network.ReminderApiService

class ReminderRepository(private val apiService: ReminderApiService) {
    suspend fun getReminders(): List<Reminder> {
        return apiService.getAllReminders()
    }

    suspend fun addReminder(reminder: Reminder): Any {
        return apiService.addReminder(reminder)
    }
}
