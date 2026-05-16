package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.myapplication.ReminderDatabaseHelper as DBH

class ReminderRepository(context: Context) {
    private val db = DBH(context)

    fun addReminder(reminder: Reminder): Long {
        val cv = ContentValues().apply {
            put(DBH.COL_TITLE, reminder.title)
            put(DBH.COL_DESCRIPTION, reminder.description)
            put(DBH.COL_REMIND_TIME, reminder.remindTime)
            put(DBH.COL_DATE, reminder.date)
            put(DBH.COL_IS_STATUS, reminder.isStatus)
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
                        title = it.getString(it.getColumnIndexOrThrow(DBH.COL_TITLE)),
                        description = it.getString(it.getColumnIndexOrThrow(DBH.COL_DESCRIPTION)),
                        remindTime = it.getString(it.getColumnIndexOrThrow(DBH.COL_REMIND_TIME)),
                        date = it.getString(it.getColumnIndexOrThrow(DBH.COL_DATE)),
                        isStatus = it.getInt(it.getColumnIndexOrThrow(DBH.COL_IS_STATUS))
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
            "SELECT COUNT(*) FROM ${DBH.TABLE_REMINDERS} WHERE ${DBH.COL_IS_STATUS} = 1",
            null
        )
        return cursor.use { if (it.moveToFirst()) it.getInt(0) else 0 }
    }
}
