package com.practice.daily_task.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.practice.daily_task.database.Todo


@Database(entities =[Todo::class] , version = 5)
@TypeConverters(Converters::class)
abstract class TodoDatabase : RoomDatabase(){
    abstract fun geTodoDao() : TodoDao

}