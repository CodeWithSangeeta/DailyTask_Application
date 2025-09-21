package com.practice.daily_task.todoUI

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat
import com.practice.daily_task.database.TodoDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.util.Calendar

class ReminderActionReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val todoId = intent.getIntExtra("todoId",-1)
        when(intent.action) {
            "MARK_DONE" -> {
                CoroutineScope(Dispatchers.IO).launch {
                    val db = TodoDatabase.getDatabase(context)
                    val todoDao = db.getTodoDao()
                    val todo = todoDao.getTodoById(todoId).firstOrNull()
                    todo?.let {
                        val updated = it.copy(isMarked = true)
                        todoDao.updateTodo(updated)

                        // cancel notification
                        NotificationManagerCompat.from(context).cancel(todoId)
                    }
                }

                Toast.makeText(context, "Marked as done", Toast.LENGTH_SHORT).show()
            }

            "SNOOZE" -> {
                CoroutineScope(Dispatchers.IO).launch {
                    val db = TodoDatabase.getDatabase(context)
                    val dao = db.getTodoDao()
                    val todo = dao.getTodoById(todoId).firstOrNull()
                    todo?.let {
                        val newTime = System.currentTimeMillis() + 10 * 60_000L // +10 min from now
                        val updated = it.copy(reminderTime = newTime)
                        dao.updateTodo(updated)

                        // reschedule:
                        val calendar = Calendar.getInstance().apply { timeInMillis = newTime }
                        scheduleAlarm(context, calendar, updated)
                        // cancel old notification
                        NotificationManagerCompat.from(context).cancel(todoId)
                    }
                }

                Toast.makeText(context, "Task snoozed for 10 mins", Toast.LENGTH_SHORT).show()
            }
        }
        }
    }

