package com.practice.daily_task.todoUI

import android.R.attr.id
import android.R.attr.priority
import android.R.id.title
import android.graphics.Paint

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.practice.daily_task.R
import com.practice.daily_task.database.Todo
import com.practice.daily_task.database.TodoViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale




@Composable
fun DetailScreen(todoId : Int, viewModel: TodoViewModel) {
    val todo = viewModel.getTodoById(todoId).collectAsState(initial = null)

    var isEditing by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var dueDate by remember { mutableStateOf<Date?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedPriority by remember {mutableStateOf(Priority.None)}
    var isReminderSet by remember { mutableStateOf(false) }

    LaunchedEffect(todo.value) {
        todo.value?.let {
            title = it.title
            description = it.description
            dueDate = it.dueDate
            selectedPriority = it.priority
        }
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier
                                .size(32.dp)
                        )
                    }
                    Text(
                        text = "Task Detail",
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 12.dp, top = 4.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )

                Spacer(modifier = Modifier.height(20.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outline)
            }
        }
    )
    { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
        ) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .size(500.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.outline
                    ),
                        colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                        ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            )
            {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    if (isEditing) {
                        OutlinedTextField(
                            value = title,
                            onValueChange = { title = it },
                            label = { Text("Title") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        Text(
                            text = title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        //for add up priority
                        PriorityChip(
                            selectedPriority = selectedPriority,
                            onPrioritySelected = {
                                    selectedPriority = it
                            },
                            enabled = isEditing
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        AssistChip(
                            onClick = {
                                if(isEditing) {
                                    showDatePicker = true
                                }
                            },
                            label = { Text(
                                text = dueDate?.let{
                                    "Due: ${SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(it)}"
                                }?:"Set Due Date") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.DateRange,
                                    contentDescription = null,
                                    modifier = Modifier.size(AssistChipDefaults.IconSize)
                                )
                            },
                        )
                    }

                    if(showDatePicker) {
                        DueDatePicker(selectedDate = dueDate,
                            onDateSelected = { dueDate = it
                                showDatePicker = false},
                            onDismiss = {showDatePicker = false})// close picker if dismissed
                    }


                    Text(
                        text = "Description",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    if (isEditing) {
                        OutlinedTextField(
                            value = description,
                            onValueChange = { description = it },
                            label = { Text("Description") },
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Text(
                            text = description,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }






            Spacer(modifier = Modifier.height(12.dp))

            if(isEditing){
                ElevatedButton(
                    onClick = {
                        viewModel.updateTodo(todoId, title, description,dueDate, selectedPriority, isReminderSet = isReminderSet,)
                        isEditing = !isEditing
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.mark_icon),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Save Changes",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            else{
            ElevatedButton(
                onClick = {
                    if (isEditing) {
                        viewModel.updateTodo(todoId, title, description,dueDate ,selectedPriority,isReminderSet)
                    }
                    isEditing = !isEditing
                },
                modifier = Modifier
                    .fillMaxWidth()
                    //.padding(8.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(10.dp)
                    ),
               // shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.edit_icon),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Edit Task",
                    fontSize = 16.sp,
                )
            }
             }

           if(!isEditing) {
               ElevatedButton(
                   onClick = {
                       viewModel.markTodo(todoId,dueDate)
                   },
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(8.dp),
                   shape = RoundedCornerShape(12.dp),
                   colors = ButtonDefaults.elevatedButtonColors(
                       containerColor = MaterialTheme.colorScheme.primary,
                       contentColor = MaterialTheme.colorScheme.onPrimary
                   )
               ) {
                   Icon(
                       painter = painterResource(id = R.drawable.mark_icon),
                       contentDescription = null,
                       modifier = Modifier.size(18.dp)
                   )
                   Spacer(modifier = Modifier.width(8.dp))
                   Text(
                       "Mark as Complete",
                       fontSize = 16.sp,
                       fontWeight = FontWeight.Bold
                   )
               }
           }

        }
    }
}



