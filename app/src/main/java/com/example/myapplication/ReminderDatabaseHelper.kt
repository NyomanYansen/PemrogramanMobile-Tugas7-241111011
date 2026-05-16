package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ReminderDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private const val DB_NAME    = "reminder_app.db"
        private const val DB_VERSION = 2 // Incremented version
        const val TABLE_REMINDERS    = "reminders"

        const val COL_ID          = "id"
        const val COL_TITLE       = "title"
        const val COL_DESCRIPTION = "description"
        const val COL_REMIND_TIME = "remind_time"
        const val COL_DATE        = "date"
        const val COL_IS_STATUS   = "is_status"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_REMINDERS (
                $COL_ID          INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_TITLE       TEXT    NOT NULL,
                $COL_DESCRIPTION TEXT,
                $COL_REMIND_TIME TEXT    NOT NULL,
                $COL_DATE        TEXT    NOT NULL,
                $COL_IS_STATUS   INTEGER NOT NULL DEFAULT 1
            )
        """.trimIndent()
        db.execSQL(createTable)
        insertSampleData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_REMINDERS")
        onCreate(db)
    }

    private fun insertSampleData(db: SQLiteDatabase) {
        val sampleReminders = listOf(
            arrayOf("Tugas Pemrograman Mobile", "Menyelesaikan Tugas Besar Membuat Aplikasi Mobile", "09:45", "2026-05-14"),
            arrayOf("Tugas Statistika", "Menyelesaikan soal latihan di halaman 94", "23:59", "2026-05-14"),
            arrayOf("Persiapan Presentasi UX", "Menyusun slide presentasi mengenai Tugas Besar", "10:00", "2026-05-13")
        )
        sampleReminders.forEach { r ->
            val cv = ContentValues().apply {
                put(COL_TITLE, r[0])
                put(COL_DESCRIPTION, r[1])
                put(COL_REMIND_TIME, r[2])
                put(COL_DATE, r[3])
                put(COL_IS_STATUS, 1)
            }
            db.insert(TABLE_REMINDERS, null, cv)
        }
    }
}
