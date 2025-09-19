package com.practice.daily_task.todoUI

import android.R.attr.priority
import android.R.attr.title
import android.widget.Toast
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.room.util.query
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
    val query by viewModel.searchQuery.collectAsState()
    val tasks by viewModel.filteredTasks.collectAsState(initial = emptyList())


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
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(65.dp),

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
                .padding(8.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
        ) {
            SortSearch(
                query = query,
                onQueryChange = {
                    viewModel.updateSearchQuery(it)
                },
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = SimpleDateFormat(
                    "EEEE , h:mm a",
                    Locale.ENGLISH
                ).format(System.currentTimeMillis()),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = SimpleDateFormat(
                    "MMMM d, yyyy ",
                    Locale.ENGLISH
                ).format(System.currentTimeMillis()),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(12.dp))


//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                shape = RoundedCornerShape(16.dp),
//                colors = CardDefaults.cardColors(
//                    containerColor = MaterialTheme.colorScheme.surface,
//                    contentColor = MaterialTheme.colorScheme.onSurface
//                ),
//                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
//            ) {
//                Column(modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp)) {
//                    Text("Your Daily Focus", fontSize = 24.sp, fontWeight = FontWeight.Bold)
//                    Spacer(modifier = Modifier.height(12.dp))
//                    Text(
//                        stringResource(R.string.quotesPart1) +
//                                stringResource(R.string.quotesPart2),
//                        fontSize = 16.sp,
//                        color = Color.Gray,
//                        maxLines = Int.MAX_VALUE //allow multiple lines
//                    )
//
//                }
//            }


            //make safe call if list is empty then it only print text otherwise it call lazycolumn
//            val currentTodoList = todoList
//            currentTodoList?.let { list ->
//                if (list.isEmpty()) {
            if (tasks.isEmpty()) {
                val emptyMessage = if(query.isBlank()){
                    "No Task Added Yet"
                }else{
                    "No tasks match your search"
                }
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = emptyMessage,
                    fontSize = 16.sp
                )
            } else {
                LazyColumn {
                    itemsIndexed(tasks) { index, item ->
                        TodoItem(
                            item = item,
                            onClick = { navController.navigate("${routes.DetailScreen}/${item.id}") },
                            onDelete = { viewModel.deleteTodo(item.id) })
                    }
                }
            }
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
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {

                Column(modifier = Modifier.weight(1f)) {


                    Row {
                        if(item.isMarked) {
                            Icon(
                                painter = painterResource(id = R.drawable.mark_icon),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(16.dp)
                                    .align(Alignment.CenterVertically)
                            )
                        }

                        if (item.priority != Priority.None ) {
                            Spacer(modifier = Modifier.width(8.dp))
                        }

                        Text(
                            text = item.title,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Text(
                        text = item.description,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
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
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                         Spacer(modifier = Modifier.width(8.dp))

                        if(item.priority != Priority.None) {
                            Icon(
                                painter = painterResource(id = R.drawable.flag),
                                contentDescription = null,
                                tint = item.priority.color,
                                modifier = Modifier
                                    .size(12.dp)
                                    .align(Alignment.CenterVertically)
                            )
                        }

                        if (item.priority != Priority.None && item.isReminderSet) {
                            Spacer(modifier = Modifier.width(8.dp))
                        }

                           val currentTime = System.currentTimeMillis()
                            if( item.isReminderSet && item.reminderTime!! >= currentTime){
                                    Icon(
                                        imageVector = Icons.Filled.Notifications,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier
                                            .size(14.dp)
                                            .align(Alignment.CenterVertically)
                                    )
                            }



                    }


                }
                IconButton(onClick = onDelete) {
                    Icon(
                        painter = painterResource(id = _root_ide_package_.com.practice.daily_task.R.drawable.delete_icon),
                        modifier = Modifier.size(20.dp),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

        }
    }



@Composable
fun SortSearch(
    query : String,
    onQueryChange : (String) -> Unit
) {
    var isSheetOpen by rememberSaveable { mutableStateOf(false) }
    var selectedSort by rememberSaveable {mutableStateOf ("Due Date (Earliest First)")}
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = {
                onQueryChange(it)
            },
            placeholder = {
                Text(
                    text = "Search Task...",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                )
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
                .height(56.dp)
                .weight(1f),
            singleLine = true,
            maxLines = 1,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                cursorColor = MaterialTheme.colorScheme.primary
            )
        )
        Spacer(modifier = Modifier.width(16.dp))

        IconButton(
            onClick = {isSheetOpen=true},
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.sort_icon),
                contentDescription = "Sort",
                modifier = Modifier.size(36.dp)
            )
        }

        if(isSheetOpen) {
            BottomSheet(
                onDismiss = { isSheetOpen = false },
                selectedSort = selectedSort,
                onSortSelected = {selectedSort = it}
            )
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    selectedSort : String,
    onSortSelected : (String) -> Unit,
    onDismiss: () -> Unit
    ){
//    val sheetState = rememberModalBottomSheetState()

        ModalBottomSheet(
            onDismissRequest = {
              onDismiss()
            }
        ) {
           Column(modifier = Modifier
               .fillMaxWidth()
               .padding(16.dp)) {
               Text("Sort By", style = MaterialTheme.typography.titleMedium.copy(
                   color = MaterialTheme.colorScheme.primary
               ))

               Spacer(modifier = Modifier.height(12.dp))


               // Due Date
               Text("Due Date",
                   style = MaterialTheme.typography.bodyMedium.copy(
                       color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                   ))
               Row(verticalAlignment = Alignment.CenterVertically) {
                   RadioButton(
                       selected = selectedSort == "due_asc",
                       onClick = { onSortSelected("due_asc") }
                   )
                   Text("Earliest First")
               }
               Row(verticalAlignment = Alignment.CenterVertically) {
                   RadioButton(
                       selected = selectedSort == "due_desc",
                       onClick = { onSortSelected("due_desc") }
                   )
                   Text("Latest First")
               }

               // Priority
               Text("Priority")
               Row(verticalAlignment = Alignment.CenterVertically) {
                   RadioButton(
                       selected = selectedSort == "priority_high",
                       onClick = { onSortSelected("priority_high") }
                   )
                   Text("High → Low")
               }
               Row(verticalAlignment = Alignment.CenterVertically) {
                   RadioButton(
                       selected = selectedSort == "priority_low",
                       onClick = { onSortSelected("priority_low") }
                   )
                   Text("Low → High")
               }

               // Due Date
               Text("Status")
               Row(verticalAlignment = Alignment.CenterVertically) {
                   RadioButton(
                       selected = selectedSort == "state_complete",
                       onClick = { onSortSelected("state_complete") }
                   )
                   Text("Complete First")
               }
               Row(verticalAlignment = Alignment.CenterVertically) {
                   RadioButton(
                       selected = selectedSort == "state_notComplete",
                       onClick = { onSortSelected("state_notComplete") }
                   )
                   Text("Incomplete  First")
               }

               //Title
               Text("Title")
               Row(verticalAlignment = Alignment.CenterVertically) {
                   RadioButton(
                       selected = selectedSort == "title_asc",
                       onClick = { onSortSelected("title_asc") }
                   )
                   Text("A -> Z")
               }
               Row(verticalAlignment = Alignment.CenterVertically) {
                   RadioButton(
                       selected = selectedSort == "title_desc",
                       onClick = { onSortSelected("title_desc") }
                   )
                   Text("Z -> A")
               }

           }
        }
    }







