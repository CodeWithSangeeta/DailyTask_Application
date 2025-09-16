package com.practice.daily_task.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Profile (
    @PrimaryKey
    val id : Int = 1,
    val name : String,
    val email : String,
    val phone : String,
    val profilePicPath : String? = null
)



