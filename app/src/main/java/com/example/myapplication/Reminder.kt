package com.example.myapplication

import com.google.gson.annotations.SerializedName

data class Reminder(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("remind_time") val remindTime: String, // Contoh: "08:00"
    @SerializedName("date") val date: String,             // Contoh: "2026-05-20"
    @SerializedName("is_status") val isStatus: Int = 1    // 1 untuk aktif, 0 untuk selesai
)
