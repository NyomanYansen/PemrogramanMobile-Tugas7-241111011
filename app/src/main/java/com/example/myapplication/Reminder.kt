package com.example.myapplication

data class Reminder(
    val id: Int = 0,
    val courseCode: String,
    val title: String,
    val description: String,
    val deadline: String,
    val color: String = "#3B82F6" // Default blue
)
