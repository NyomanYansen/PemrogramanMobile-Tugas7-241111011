package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ScheduleDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private const val DB_NAME = "schedule_app.db"
        private const val DB_VERSION = 1
        const val TABLE_COURSES = "courses"

        const val COL_ID = "id"
        const val COL_DAY = "day"
        const val COL_CODE = "code"
        const val COL_NAME = "name"
        const val COL_TIME = "time"
        const val COL_ROOM = "room"
        const val COL_LECTURER = "lecturer"
        const val COL_COLOR = "color"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_COURSES (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_DAY TEXT NOT NULL,
                $COL_CODE TEXT NOT NULL,
                $COL_NAME TEXT NOT NULL,
                $COL_TIME TEXT NOT NULL,
                $COL_ROOM TEXT NOT NULL,
                $COL_LECTURER TEXT NOT NULL,
                $COL_COLOR TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTable)
        insertSampleData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_COURSES")
        onCreate(db)
    }

    private fun insertSampleData(db: SQLiteDatabase) {
        val courses = listOf(
            arrayOf("SENIN", "SIF61114", "e-Government", "12:45 - 14:25", "C206", "Dr. R.A.E. Virgana Targa Sapanji, S.Kom., M.T.", "#3B82F6"),
            arrayOf("SENIN", "SIF62110", "Pemrograman Mobile", "09:45 - 11:25", "B409 - Lab. Komputer Advance", "Dani Hamdani, S.Kom., M.T.", "#3B82F6"),
            arrayOf("SELASA", "SIF62116", "Pengantar Kecerdasan Buatan", "09:45 - 11:25", "C218", "Rikky Wisnu Nugraha, S.T., M.Kom., MOS., MCE., MTA.", "#10B981"),
            arrayOf("SELASA", "SIF62117", "Manajemen Risiko IT", "12:45 - 14:25", "K105", "IR. UCU NUGRAHA, S.T., M.KOM., IPM., MOS.", "#EF4444"),
            arrayOf("RABU", "SIF62127", "SAP AC010 Business Processes", "09:45 - 12:15", "B409 - Lab. Komputer Advance", "John Henry Wijaya, S.E., M.M., MOS.", "#3B82F6"),
            arrayOf("RABU", "SIF62119", "Desain Pengalaman Pengguna", "12:45 - 15:15", "C216", "IR. UCU NUGRAHA, S.T., M.KOM., IPM., MOS.", "#8B5CF6"),
            arrayOf("KAMIS", "ENG62110", "Statistika", "13:45 - 15:15", "B409 - Lab. Komputer", "IR. SRI LESTARI, M.T.", "#10B981"),
            arrayOf("KAMIS", "SIF62115", "Sains Data", "09:45 - 12:15", "B415 - Lab. Komputer Fundamental", "Dr. R.A.E. Virgana Targa Sapanji, S.Kom., M.T.", "#3B82F6")
        )

        courses.forEach { c ->
            val cv = ContentValues().apply {
                put(COL_DAY, c[0])
                put(COL_CODE, c[1])
                put(COL_NAME, c[2])
                put(COL_TIME, c[3])
                put(COL_ROOM, c[4])
                put(COL_LECTURER, c[5])
                put(COL_COLOR, c[6])
            }
            db.insert(TABLE_COURSES, null, cv)
        }
    }
}
