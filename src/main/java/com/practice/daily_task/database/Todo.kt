package com.practice.daily_task.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.practice.daily_task.todoUI.Priority
import java.util.Date

@Entity
data class Todo (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val createdAt: Date,
    val dueDate: Date? = null, // nullable -> user may not set a due date
    val priority: Priority = Priority.None, //nullable

)