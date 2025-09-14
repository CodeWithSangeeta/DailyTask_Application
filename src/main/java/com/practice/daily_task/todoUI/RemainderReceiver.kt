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


class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            // Build notification
            val notification = NotificationCompat.Builder(context, "task_reminder_channel")
                .setSmallIcon(R.drawable.ic_launcher_foreground) // Change to your app icon
                .setContentTitle("Sangeeta")
                .setContentText("Good job")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build()

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU || ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            with(NotificationManagerCompat.from(context)) {
                notify(System.currentTimeMillis().toInt(), notification)
            }
        }


    }
    }

