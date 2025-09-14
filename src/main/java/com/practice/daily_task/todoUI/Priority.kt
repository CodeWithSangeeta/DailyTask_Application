package com.practice.daily_task.todoUI

import androidx.compose.ui.graphics.Color

enum class Priority (
    val label : String,
    val color : Color
){
    High("High Priority", color = Color(0xFFFF3B30)),
    Medium("Medium Priority", color = Color(0xFFFF9500)),
    Low("Low Priority", color = Color(0xFF34C759)),
    None("Set Priority", color = Color.Gray)
}