package com.practice.daily_task.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Todo (
    @PrimaryKey(autoGenerate = true)
    val id : Int=0,
    val title : String,
    val description : String,
    val createdAt : Date
)