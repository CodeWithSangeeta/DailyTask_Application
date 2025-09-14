package com.practice.daily_task.todoUI


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import java.util.Calendar

fun showDateTimePicker(context: Context, onDateTimeSelected: (Calendar) -> Unit) {
    val currentCalendar = Calendar.getInstance()

    // Date Picker
    DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            // After date selected, show Time Picker
            TimePickerDialog(
                context,
                { _, hour, minute ->
                    val selectedCalendar = Calendar.getInstance()
                    selectedCalendar.set(year, month, dayOfMonth, hour, minute, 0)
                    onDateTimeSelected(selectedCalendar)
                },
                currentCalendar.get(Calendar.HOUR_OF_DAY),
                currentCalendar.get(Calendar.MINUTE),
                true
            ).show()
        },
        currentCalendar.get(Calendar.YEAR),
        currentCalendar.get(Calendar.MONTH),
        currentCalendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}
