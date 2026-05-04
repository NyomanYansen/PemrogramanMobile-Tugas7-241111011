package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.myapplication.ReminderDatabaseHelper as DBH

class ReminderRepository(context: Context) {
    private val db = DBH(context)

    fun addReminder(reminder: Reminder): Long {
        val cv = ContentValues().apply {
            put(DBH.COL_COURSE_CODE, reminder.courseCode)
            put(DBH.COL_TITLE, reminder.title)
            put(DBH.COL_DESCRIPTION, reminder.description)
            put(DBH.COL_DEADLINE, reminder.deadline)
            put(DBH.COL_COLOR, reminder.color)
            put(DBH.COL_IS_ACTIVE, 1)
        }
        return db.writableDatabase.insert(DBH.TABLE_REMINDERS, null, cv)
    }

    fun getAllReminders(): List<Reminder> {
        val reminders = mutableListOf<Reminder>()
        val cursor: Cursor = db.readableDatabase.query(
            DBH.TABLE_REMINDERS, null, null, null, null, null,
            "${DBH.COL_ID} DESC"
        )
        cursor.use {
            while (it.moveToNext()) {
                reminders.add(
                    Reminder(
                        id = it.getInt(it.getColumnIndexOrThrow(DBH.COL_ID)),
                        courseCode = it.getString(it.getColumnIndexOrThrow(DBH.COL_COURSE_CODE)),
                        title = it.getString(it.getColumnIndexOrThrow(DBH.COL_TITLE)),
                        description = it.getString(it.getColumnIndexOrThrow(DBH.COL_DESCRIPTION)),
                        deadline = it.getString(it.getColumnIndexOrThrow(DBH.COL_DEADLINE)),
                        color = it.getString(it.getColumnIndexOrThrow(DBH.COL_COLOR)) ?: "#3B82F6"
                    )
                )
            }
        }
        return reminders
    }

    fun deleteReminder(id: Int): Int {
        return db.writableDatabase.delete(
            DBH.TABLE_REMINDERS,
            "${DBH.COL_ID} = ?",
            arrayOf(id.toString())
        )
    }

    fun countActiveReminders(): Int {
        val cursor = db.readableDatabase.rawQuery(
            "SELECT COUNT(*) FROM ${DBH.TABLE_REMINDERS} WHERE ${DBH.COL_IS_ACTIVE} = 1",
            null
        )
        return cursor.use { if (it.moveToFirst()) it.getInt(0) else 0 }
    }
}
