package com.practice.daily_task.todoUI

import android.R.attr.description
import android.R.attr.title
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat

import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.practice.daily_task.R
import android.Manifest
import android.app.PendingIntent
import com.practice.daily_task.database.Todo
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val todoId = intent.getIntExtra("todoId", -1)
        val title = intent.getStringExtra("title")?: "Task Reminder"
        val dueDateMillis = intent.getLongExtra("dueDate", -1L)
        val priority = intent.getStringExtra("priority") ?: "None"
        val dueDate = if (dueDateMillis != -1L) Date(dueDateMillis) else null


        val priorityIcon = when(priority){
            "High" -> "\u26A0️"      // ⚠️
            "Medium" -> "\u26A1️"    // ⚡
            "Low" -> "\u2705"        // ✅
            else -> ""
        }

        val dueText = dueDate?.let {
            "🗓 ${SimpleDateFormat("dd MMM", Locale.getDefault()).format(it)}"
        } ?: ""

        // Intent for "Mark Done" Action
        val markDoneIntent = Intent(context, ReminderActionReceiver::class.java).apply {
            action = "MARK_DONE"
            putExtra("todoId",todoId)
        }

        val markDonePendingIntent = PendingIntent.getBroadcast(
            context,
            todoId,
            markDoneIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Intent for "Snooze" Action
        val snoozeIntent = Intent(context, ReminderActionReceiver::class.java).apply {
            action = "SNOOZE"
            putExtra("todoId",todoId)
        }

        val snoozePendingIntent = PendingIntent.getBroadcast(
            context,
            todoId + 1,
            snoozeIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )


            // Build notification
            val notification = NotificationCompat.Builder(context, "task_reminder_channel")
                .setSmallIcon(R.drawable.edit_icon)
                .setContentTitle(title)
                .setContentText("$dueText  $priorityIcon")
                .setStyle(
                    NotificationCompat.BigTextStyle().bigText(
                        "$dueText  $priorityIcon"
                    )
                )
                .addAction(R.drawable.mark_icon,"Mark Done",markDonePendingIntent)
                .addAction(R.drawable.snooze_icon,"Snooze",snoozePendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build()

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU || ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            with(NotificationManagerCompat.from(context)) {
                notify(if(todoId != 1) todoId else System.currentTimeMillis().toInt(), notification)
            }
        }


    }
    }

