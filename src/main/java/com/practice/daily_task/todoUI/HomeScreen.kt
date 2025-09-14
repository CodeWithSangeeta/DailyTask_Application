package com.practice.daily_task.todoUI

import android.R.attr.priority
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.practice.daily_task.R
import com.practice.daily_task.database.TodoViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import com.practice.daily_task.database.Todo
import com.practice.daily_task.routes


@Composable
fun HomeScreen(navController: NavController, viewModel: TodoViewModel) {

    val todoList by viewModel.todoList.collectAsState()
    var selectedTab by rememberSaveable { mutableStateOf(0) }



    MainScaffold(
        navController = navController,
        selectedItemIndex = selectedTab,
        onItemSelectedIndex = { index ->
            when (index) {
                0 -> navController.navigate(com.practice.daily_task.routes.HomeScreen)
                1 -> navController.navigate(com.practice.daily_task.routes.AddTask)
                2 -> navController.navigate(com.practice.daily_task.routes.Profile)
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(com.practice.daily_task.routes.AddTask)
                },
                containerColor = Color.Gray,
                modifier = Modifier.size(65.dp),
                contentColor = Color.White
            ) {
                Icon(
                    painter = painterResource(id = com.practice.daily_task.R.drawable.add_icon),
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )
            }
        },
        topBar = {
            TopBar(
                title = "Daily Tasks",
            )
        },


        ) { innerPadding ->


        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
        ) {
            Text(
                text = SimpleDateFormat(
                    "EEEE , h:mm a",
                    Locale.ENGLISH
                ).format(System.currentTimeMillis()),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Text(
                text = SimpleDateFormat(
                    "MMMM d, yyyy ",
                    Locale.ENGLISH
                ).format(System.currentTimeMillis()),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,

            )
            Spacer(modifier = Modifier.height(12.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(contentColor = Color.Black),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)) {
                    Text("Your Daily Focus", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        stringResource(R.string.quotesPart1) +
                                stringResource(R.string.quotesPart2),
                        fontSize = 16.sp,
                        color = Color.Gray,
                        maxLines = Int.MAX_VALUE //allow multiple lines
                    )

                }
            }

            //make safe call if list is empty then it only print text otherwise it call lazycolumn
            val currentTodoList = todoList
            currentTodoList?.let { list ->
                if (list.isEmpty()) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "No Item Added Yet",
                        fontSize = 16.sp
                    )
                } else {
                    LazyColumn(
                        content = {
                            itemsIndexed(list) { index, item ->
                                TodoItem(
                                    item = item,
                                    onClick = {navController.navigate("${routes.DetailScreen}/${item.id}")},
                                    onDelete = { viewModel.deleteTodo(item.id) })
                            }
                        }
                    )
                }
            } ?: Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "No Task Added Yet",
                fontSize = 16.sp
            )

        }
    }
}





    @Composable
    fun TodoItem(item: Todo, onClick : () -> Unit ,onDelete: () -> Unit) {


        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { onClick() }
                .clip(RoundedCornerShape(16.dp))
                .padding(4.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = item.description,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row {
                        Text(
                            text = SimpleDateFormat(
                                "MMM dd, h:mm a",
                                Locale.ENGLISH
                            ).format(item.createdAt),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray
                        )

                         Spacer(modifier = Modifier.width(8.dp))

                        if(item.priority != Priority.None) {
                            Icon(
                                painter = painterResource(id = R.drawable.flag),
                                contentDescription = null,
                                tint = item.priority.color,
                                modifier = Modifier.size(12.dp)
                                    .align(Alignment.CenterVertically)
                            )
                        }

                        if (item.priority != Priority.None && item.isReminderSet) {
                            Spacer(modifier = Modifier.width(8.dp))
                        }


                            if( item.isReminderSet){
                              // if (item.dueDate == null  || item.reminderTime <= item.dueDate.time ) {
                                    Icon(
                                        imageVector = Icons.Filled.Notifications,
                                        contentDescription = null,
                                        modifier = Modifier.size(14.dp)
                                            .align(Alignment.CenterVertically)
                                    )
                               // }
                            }



                    }


                }
                IconButton(onClick = onDelete) {
                    Icon(
                        painter = painterResource(id = _root_ide_package_.com.practice.daily_task.R.drawable.delete_icon),
                        modifier = Modifier.size(20.dp),
                        contentDescription = null
                    )
                }
            }

        }
    }



