package com.practice.daily_task.database

import androidx.room.TypeConverter
import com.practice.daily_task.todoUI.Priority
import java.util.Date

class Converters {
    @TypeConverter
    fun fromDate(date: Date): Long{
        return date.time
    }
    @TypeConverter
    fun toDate(millisSinceEpoch: Long): Date {
        return Date(millisSinceEpoch)
    }
}