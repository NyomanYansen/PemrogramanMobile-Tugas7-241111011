package com.example.myapplication

import android.content.Context
import android.database.Cursor
import com.example.myapplication.ScheduleDatabaseHelper as DBH

class CourseRepository(context: Context) {
    private val db = DBH(context)

    fun getAllCourses(): List<Course> {
        val courses = mutableListOf<Course>()
        val cursor: Cursor = db.readableDatabase.query(
            DBH.TABLE_COURSES, null, null, null, null, null,
            "${DBH.COL_ID} ASC"
        )
        cursor.use {
            while (it.moveToNext()) {
                courses.add(
                    Course(
                        id = it.getInt(it.getColumnIndexOrThrow(DBH.COL_ID)),
                        day = it.getString(it.getColumnIndexOrThrow(DBH.COL_DAY)),
                        code = it.getString(it.getColumnIndexOrThrow(DBH.COL_CODE)),
                        name = it.getString(it.getColumnIndexOrThrow(DBH.COL_NAME)),
                        time = it.getString(it.getColumnIndexOrThrow(DBH.COL_TIME)),
                        room = it.getString(it.getColumnIndexOrThrow(DBH.COL_ROOM)),
                        lecturer = it.getString(it.getColumnIndexOrThrow(DBH.COL_LECTURER)),
                        color = it.getString(it.getColumnIndexOrThrow(DBH.COL_COLOR))
                    )
                )
            }
        }
        return courses
    }

    fun getCoursesByDay(day: String): List<Course> {
        val courses = mutableListOf<Course>()
        val cursor: Cursor = db.readableDatabase.query(
            DBH.TABLE_COURSES, null,
            "${DBH.COL_DAY} = ?", arrayOf(day),
            null, null, null
        )
        cursor.use {
            while (it.moveToNext()) {
                courses.add(
                    Course(
                        id = it.getInt(it.getColumnIndexOrThrow(DBH.COL_ID)),
                        day = it.getString(it.getColumnIndexOrThrow(DBH.COL_DAY)),
                        code = it.getString(it.getColumnIndexOrThrow(DBH.COL_CODE)),
                        name = it.getString(it.getColumnIndexOrThrow(DBH.COL_NAME)),
                        time = it.getString(it.getColumnIndexOrThrow(DBH.COL_TIME)),
                        room = it.getString(it.getColumnIndexOrThrow(DBH.COL_ROOM)),
                        lecturer = it.getString(it.getColumnIndexOrThrow(DBH.COL_LECTURER)),
                        color = it.getString(it.getColumnIndexOrThrow(DBH.COL_COLOR))
                    )
                )
            }
        }
        return courses
    }
}
