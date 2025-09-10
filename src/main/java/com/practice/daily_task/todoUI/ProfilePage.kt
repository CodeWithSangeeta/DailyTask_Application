package com.practice.daily_task.todoUI

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.practice.daily_task.R
import com.practice.daily_task.database.TodoViewModel
import com.practice.daily_task.routes


@Composable
fun ProfilePage(navcontroller: NavController,viewModel: TodoViewModel) {

    var selectedTab by rememberSaveable { mutableStateOf(2) }
    var firstname by rememberSaveable { mutableStateOf("") }
    var lastname by rememberSaveable { mutableStateOf("") }
    var mobilNo by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    val genders = listOf("Male", "Female")
    var selectedGender by remember { mutableStateOf(genders[1]) }
    var genderExpanded by remember { mutableStateOf(false) }

    var displayName by rememberSaveable { mutableStateOf("") }
    var displayEmail by rememberSaveable { mutableStateOf("") }

    // Controls visibility of the personal information card
    var isEditing by rememberSaveable { mutableStateOf(true) }


    MainScaffold(
        navController = navcontroller,
        selectedItemIndex = selectedTab,
        onItemSelectedIndex = { index ->
            when (index) {
                0 -> navcontroller.navigate(routes.HomeScreen)
                1 -> navcontroller.navigate(routes.AddTask)
                2 -> navcontroller.navigate(routes.Profile)
            }
        },
        topBar = {
            TopBar(
                title = "Profile",
            )
        },


        ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {


            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        // Profile Picture Placeholder
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .background(Color.LightGray)
                                .clickable {
                                    // Open image picker here
                                },
                           // contentAlignment = Alignment.Center
                        ) {
                            IconButton(onClick = {}){
                                Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Add Profile Picture",
                                tint = Color.Black,
                                modifier = Modifier
                                    .size(32.dp)
                                    .align(Alignment.BottomEnd)
                                    .background(Color.White, shape = CircleShape)
                                    .padding(8.dp)
                                    .offset(4.dp,4.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        if (displayName.isNotEmpty()) {
                            Text(
                                text = displayName, fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        if (displayEmail.isNotEmpty()) {
                            Text(
                                text = displayEmail,
                                fontSize = 14.sp
                            )
                        }
                    }

                }
                Spacer(modifier = Modifier.height(20.dp))
            }


                item {
                    if (isEditing) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    stringResource(R.string.personal_information),
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = stringResource(R.string.first_name),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                OutlinedTextField(
                                    value = firstname,
                                    onValueChange = { firstname = it },
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.width(360.dp),
                                    singleLine = true,
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = stringResource(R.string.last_name),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                OutlinedTextField(
                                    value = lastname,
                                    onValueChange = { lastname = it },
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.width(360.dp),
                                    singleLine = true,
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = stringResource(R.string.mobile_number),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                OutlinedTextField(
                                    value = mobilNo,
                                    onValueChange = { mobilNo = it },
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.width(360.dp),
                                    singleLine = true,
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = stringResource(R.string.email),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                OutlinedTextField(
                                    value = email,
                                    onValueChange = { email = it },
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.width(360.dp),
                                    singleLine = true,
                                )

                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = stringResource(R.string.gender),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                OutlinedTextField(
                                    value = selectedGender,
                                    onValueChange = {},
                                    readOnly = true,
                                    trailingIcon = {
                                        IconButton(onClick = {
                                            genderExpanded = !genderExpanded
                                        }) {
                                            Icon(
                                                imageVector = Icons.Default.ArrowDropDown,
                                                contentDescription = null
                                            )
                                        }
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { genderExpanded = !genderExpanded }
                                )

                                // Dropdown that appears below the TextField
                                DropdownMenu(
                                    expanded = genderExpanded,
                                    onDismissRequest = { genderExpanded = false }
                                ) {
                                    genders.forEach { option ->
                                        DropdownMenuItem(
                                            text = { Text(option) },
                                            onClick = {
                                                selectedGender = option
                                                genderExpanded = false
                                            })
                                    }
                                }


                                Spacer(modifier = Modifier.height(40.dp))
                                val context = LocalContext.current
                                Button(
                                    onClick = {

                                        if(firstname.trim().isEmpty()){
                                            Toast.makeText(context, "Please enter first name", Toast.LENGTH_SHORT).show()
                                        }
                                        else if(email.trim().isEmpty()){
                                            Toast.makeText(context,"Please enter email!",Toast.LENGTH_SHORT).show()
                                        }
                                        else {
                                            displayName =
                                                "${firstname.trim()} ${lastname.trim()}".trim()
                                            displayEmail = email.trim()
                                            isEditing = false
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        "Save Changes",
                                        fontSize = 14.sp,
                                        textAlign = TextAlign.Center,
                                    )
                                }

                            }
                        }
                    } else {
                        Button(
                            onClick = { isEditing = true },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                "Edit Personal Information",
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center
                            )
                        }

                    }
                }
        }
        }

    }






