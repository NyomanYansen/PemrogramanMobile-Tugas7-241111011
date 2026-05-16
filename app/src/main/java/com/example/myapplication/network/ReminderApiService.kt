package com.example.myapplication.network

import com.example.myapplication.Reminder
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ReminderApiService {
    @GET("reminders.php") 
    suspend fun getAllReminders(): List<Reminder>

    @POST("reminders.php")
    suspend fun addReminder(@Body reminder: Reminder): Any
}
