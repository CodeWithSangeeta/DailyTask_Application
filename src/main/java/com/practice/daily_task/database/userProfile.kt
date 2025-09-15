package com.practice.daily_task.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class userProfile (
    @PrimaryKey
    val id : Int = 1,
    val name : String,
    val email : String,
    val phone : String
)



