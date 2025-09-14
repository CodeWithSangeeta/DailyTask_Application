package com.practice.daily_task.todoUI

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.practice.daily_task.R
import com.practice.daily_task.database.Todo
import com.practice.daily_task.database.TodoViewModel
import com.practice.daily_task.routes
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun AddNewTask(navController: NavController, viewModel: TodoViewModel ) {

    val todoList by viewModel.todoList.collectAsState(initial = emptyList<Todo>())
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var dueDate by remember { mutableStateOf<Date?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }

    var selectedTab by rememberSaveable { mutableStateOf(1) }

    var selectedPriority by remember {mutableStateOf(Priority.None)}
    var selectedDateTime by remember { mutableStateOf<Long?>(null) }
    var formattedDateTime by remember { mutableStateOf("Add Reminder") }
    var isReminderSet by remember { mutableStateOf(false) }


    val context = LocalContext.current

    MainScaffold(

        navController = navController,
        selectedItemIndex = selectedTab,
        onItemSelectedIndex = { index ->
            when (index) {
                0 -> navController.navigate(routes.HomeScreen)
                1 -> navController.navigate(routes.AddTask)
                2 -> navController.navigate(routes.Profile)
            }
        },
        topBar = {
            TopBar(
                title = "Add New Task",
            )
        }


    ) { innerPadding ->


        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
        ) {


            Text(
                text = stringResource(R.string.task_title),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = title,
                onValueChange = {
                    if(it.length <= 35){
                    title = it}
                    else{
                        Toast.makeText(context,
                            context.getString(R.string.title_limit), Toast.LENGTH_SHORT).show()
                    }
                                },
                placeholder = {
                    Text(
                        stringResource(R.string.title_suggestion),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.width(360.dp),
                singleLine = true,
                maxLines = 1,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                    cursorColor = MaterialTheme.colorScheme.primary
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                stringResource(R.string.short_title),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.description),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                placeholder = {
                    Text(
                        stringResource(R.string.description_suggestion),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                maxLines = 5,
                singleLine = false,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                    cursorColor = MaterialTheme.colorScheme.primary
                )

                )

            Spacer(modifier = Modifier.height(12.dp))


            Text(
                stringResource(R.string.task_detail),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                //for add up priority
                PriorityChip(
                    selectedPriority = selectedPriority,
                    onPrioritySelected = { selectedPriority = it},
                    enabled = true
                )

                Spacer(modifier = Modifier.width(12.dp))

                AssistChip(
                    onClick = {
                        showDatePicker = true
                    },
                    label = { Text(
                        text = dueDate?.let{
                            "Due: ${SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(it)}"
                        }?:"Set Due Date",
                        color = MaterialTheme.colorScheme.onSurface) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.DateRange,
                            contentDescription = null,
                            modifier = Modifier.size(AssistChipDefaults.IconSize),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )
            }

            AssistChip(
                onClick = {
                    showDateTimePicker(context) { calendar ->
                       scheduleAlarm(context, calendar)
                        selectedDateTime = calendar.timeInMillis
                        formattedDateTime = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(calendar.time)
                     isReminderSet = true
                    }
                },
                label = { Text(text = formattedDateTime,color = MaterialTheme.colorScheme.onSurface )},
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Notifications,
                        contentDescription = null,
                        modifier = Modifier.size(AssistChipDefaults.IconSize),
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

         if(showDatePicker) {
             DueDatePicker(selectedDate = dueDate,
                 onDateSelected = { dueDate = it
                 showDatePicker = false},
                 onDismiss = {showDatePicker = false})// close picker if dismissed
         }


            Button(onClick = {
                if(title.isBlank() || description.isBlank()){
                    Toast.makeText(context,"Please Add Title and Description!", Toast.LENGTH_SHORT).show()
                }else {
                    Toast.makeText(context,"Task Saved Successfully!", Toast.LENGTH_SHORT).show()

                    viewModel.addTodo(title = title,
                        description = description,
                       dueDate = dueDate,
                        selectedPriority = selectedPriority,
                        isReminderSet = isReminderSet,
                        reminderTime = selectedDateTime)
                    title = ""
                    description = ""
                    dueDate = null

                    navController.navigate(routes.HomeScreen)
                }
            },
                    colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary)
            ) {
                Text(
                    "Save Task",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }


        }
    }
}


