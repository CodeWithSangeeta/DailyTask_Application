package com.practice.daily_task.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.practice.daily_task.database.Todo


@Database(entities =[Todo::class, Profile::class] , version = 7, exportSchema = true)
@TypeConverters(Converters::class)
abstract class TodoDatabase : RoomDatabase(){
    abstract fun getTodoDao() : TodoDao
    abstract fun userProfileDao() : UserProfileDao


    //
    companion object {
        @Volatile
        private var INSTANCE: TodoDatabase? = null

        fun getDatabase(context: Context): TodoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java,
                    "todo_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}