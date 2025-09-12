package com.practice.daily_task.todoUI

import androidx.compose.ui.graphics.Color

enum class Priority (
    val label : String,
    val color : Color
){
    High("High Priority", color = Color.Red),
    Medium("Medium Priority", color = Color.Yellow),
    Low("Low Priority", color = Color.Green),
    None("Set Priority", color = Color.Gray)
}