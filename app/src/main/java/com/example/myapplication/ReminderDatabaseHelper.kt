package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ReminderDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private const val DB_NAME    = "reminder_app.db"
        private const val DB_VERSION = 1
        const val TABLE_REMINDERS    = "reminders"

        const val COL_ID          = "id"
        const val COL_COURSE_CODE = "course_code"
        const val COL_TITLE       = "title"
        const val COL_DESCRIPTION = "description"
        const val COL_DEADLINE    = "deadline"
        const val COL_IS_ACTIVE   = "is_active"
        const val COL_COLOR       = "color"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_REMINDERS (
                $COL_ID          INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_COURSE_CODE TEXT    NOT NULL,
                $COL_TITLE       TEXT    NOT NULL,
                $COL_DESCRIPTION TEXT,
                $COL_DEADLINE    TEXT    NOT NULL,
                $COL_IS_ACTIVE   INTEGER NOT NULL DEFAULT 1,
                $COL_COLOR       TEXT
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
            arrayOf("Tugas Pemrograman Mobile", "Menyelesaikan Tugas Besar Membuat Aplikasi Mobile menggunakan Android Studio berkelompok dengan mengambil projek Implementasi Siakad ke perangkat mobile (per-progress)", "Besok, 09:45", "#3B82F6"),
            arrayOf("Tugas Statistika", "Menyelesaikan soal latihan di halaman 94 dan mengunggahnya ke portal mahasiswa dalam format PDF.", "14 mei 2026", "#10B981"),
            arrayOf("Persiapan Presentasi UX", "Menyusun slide presentasi mengenai Tugas Besar Kelompok dengan topik \"Implementasi SIAKAD ke perangkat mobile\".", "13 mei 2026", "#8B5CF6")
        )
        sampleReminders.forEach { r ->
            val cv = ContentValues().apply {
                put(COL_COURSE_CODE, r[0])
                put(COL_TITLE, r[1])
                put(COL_DESCRIPTION, r[2])
                put(COL_DEADLINE, r[3])
                put(COL_COLOR, r[4])
                put(COL_IS_ACTIVE, 1)
            }
            db.insert(TABLE_REMINDERS, null, cv)
        }
    }
}
