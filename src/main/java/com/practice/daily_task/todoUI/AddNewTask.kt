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
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.practice.daily_task.R
import com.practice.daily_task.database.Todo
import com.practice.daily_task.database.TodoViewModel
import com.practice.daily_task.routes
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource


@Composable
fun AddNewTask(navController: NavController, viewModel: TodoViewModel ) {

    val todoList by viewModel.todoList.collectAsState(initial = emptyList<Todo>())
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var selectedTab by rememberSaveable { mutableStateOf(1) }

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
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = title,
                onValueChange = {
                    if(it.length <= 35){
                    title = it}
                    else{
                        Toast.makeText(context,"Title is too long!", Toast.LENGTH_SHORT).show()
                    }
                                },
                placeholder = {
                    Text(
                        stringResource(R.string.title_suggestion),
                        color = Color.Gray
                    )
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.width(360.dp),
                singleLine = true,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                stringResource(R.string.short_title),
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.description),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                placeholder = {
                    Text(
                        stringResource(R.string.description_suggestion),
                        color = Color.Gray
                    )
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                maxLines = 5,
                singleLine = false,

                )

            Spacer(modifier = Modifier.height(12.dp))


            Text(
                stringResource(R.string.task_detail),
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                AssistChip(
                    onClick = {},
                    label = { Text(text = "Add Reminder") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Notifications,
                            contentDescription = null,
                            modifier = Modifier.size(AssistChipDefaults.IconSize)
                        )
                    }
                )

                Spacer(modifier = Modifier.width(12.dp))

                AssistChip(
                    onClick = {},
                    label = { Text(text = "Set Priority") },
                    leadingIcon = {
                        Icon(
                           painter = painterResource(id=R.drawable.flag_icon),
                            contentDescription = null,
                            modifier = Modifier.size(AssistChipDefaults.IconSize)
                        )
                    }
                )
            }
            AssistChip(
                onClick = {},
                label = { Text(text = "Set Due Date") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = null,
                        modifier = Modifier.size(AssistChipDefaults.IconSize)
                    )
                }
            )

            Spacer(modifier = Modifier.height(24.dp))


            Button(onClick = {
                if(title.isBlank() || description.isBlank()){
                    Toast.makeText(context,"Please Add Title and Description!", Toast.LENGTH_SHORT).show()
                }else {
                    Toast.makeText(context,"Task Saved Successfully!", Toast.LENGTH_SHORT).show()

                    viewModel.addTodo(title, description)
                    title = ""
                    description = ""
                    navController.navigate(routes.HomeScreen)
                }
            }) {
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


