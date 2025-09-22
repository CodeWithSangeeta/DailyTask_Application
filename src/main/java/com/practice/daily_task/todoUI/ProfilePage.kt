package com.practice.daily_task.todoUI

import android.content.Intent
import android.util.Patterns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.practice.daily_task.R
import com.practice.daily_task.database.TodoViewModel
import com.practice.daily_task.routes
import com.practice.daily_task.todoUI.MainScaffold
import com.practice.daily_task.todoUI.TopBar
import org.intellij.lang.annotations.Pattern

@Composable
fun ProfilePage(navcontroller: NavController, viewModel: TodoViewModel = hiltViewModel()) {

    val profile by viewModel.userData.collectAsState(initial = null)

    var selectedTab by rememberSaveable { mutableStateOf(2) }
    var firstname by rememberSaveable { mutableStateOf("") }
    var lastname by rememberSaveable { mutableStateOf("") }
    var mobilNo by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    val genders = listOf("Male", "Female")
    var selectedGender by remember { mutableStateOf(genders[1]) }
    var genderExpanded by remember { mutableStateOf(false) }



    // Controls visibility of the personal information card
    var isEditing by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current


    //Profile pic setup
    // Profile picture launcher
    val pickLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            uri?.let {
                //You need to take persistable URI permission and then save it in your database.
                try {
                    context.contentResolver.takePersistableUriPermission(
                        it,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                } catch (e: SecurityException) {
                    e.printStackTrace()
                }
            }
            // // this block runs AFTER user picks an image (or cancels)
            viewModel.saveProfileUri(uri) // send Uri to ViewModel to save in DB

        }

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
                onBackClick = {navcontroller.popBackStack()}
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
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        IconButton(
                            onClick = { viewModel.clearUserProfile()
                                      isEditing=true},
                            modifier = Modifier.align(Alignment.TopEnd)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                modifier = Modifier.size(20.dp),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.error
                            )
                        }

                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {

                            // Profile picture container with edit icon
                            Box(
                                modifier = Modifier.size(120.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                // Profile picture circle
                                Box(
                                    modifier = Modifier
                                        .size(100.dp)
                                        .clip(CircleShape)
                                        .border(2.dp, Color.Gray, CircleShape)
                                        .background(Color.LightGray, CircleShape)
                                ) {    //show image from uri
                                    if (!profile?.profilePicPath.isNullOrEmpty()) {
                                        AsyncImage(
                                            model = profile!!.profilePicPath,
                                            contentDescription = "Profile Picture",
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier.fillMaxSize()
                                                .clip(CircleShape)
                                        )
                                    } else {
                                        Icon(
                                            painter = painterResource(id = R.drawable.default_profile_image),
                                            contentDescription = "Profile Picture",
                                            modifier = Modifier.fillMaxSize()
                                                .clip(CircleShape)
                                        )
                                    }
                                }

                                // Edit icon positioned at bottom-right edge
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .offset(
                                            x = 40.dp,
                                            y = 25.dp
                                        ) // Position it half in/out of circle
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.surface, CircleShape)
                                        .border(
                                            2.dp,
                                            MaterialTheme.colorScheme.outline,
                                            CircleShape
                                        )
                                        .clickable {
                                            pickLauncher.launch(arrayOf("image/*"))
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Edit Profile Picture",
                                        tint = MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }


                            Spacer(modifier = Modifier.height(12.dp))
                            if(profile?.name!=null) {
                                Text(
                                    text = profile!!.name,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = profile!!.email,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                Spacer(modifier = Modifier.height(20.dp))
                            }
                        }
                    }
                }
            }





            item {
                if (isEditing || profile==null) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                stringResource(R.string.personal_information),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = stringResource(R.string.first_name),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            OutlinedTextField(
                                value = firstname,
                                onValueChange = { firstname = it },
                                shape = RoundedCornerShape(8.dp),
                                placeholder = {
                                    Text("Enter first name")
                                },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                            )

                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = stringResource(R.string.last_name),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            OutlinedTextField(
                                value = lastname,
                                onValueChange = { lastname = it },
                                shape = RoundedCornerShape(8.dp),
                                placeholder = {
                                    Text("Enter last name")
                                },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                            )

                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = stringResource(R.string.mobile_number),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            OutlinedTextField(
                                value = mobilNo,
                                onValueChange = { mobilNo = it },
                                shape = RoundedCornerShape(8.dp),
                                placeholder = {
                                    Text("Enter 10 digit mobile number")
                                },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                            )

                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = stringResource(R.string.email),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            OutlinedTextField(
                                value = email,
                                onValueChange = { email = it },
                                shape = RoundedCornerShape(8.dp),
                                placeholder = {
                                    Text("Enter valid email")
                                },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                            )

                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = stringResource(R.string.gender),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
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
                                        }
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(40.dp))
                            val context = LocalContext.current
                            Button(
                                onClick = {
                                    if (firstname.trim().isEmpty() || email.trim()
                                            .isEmpty() || mobilNo.trim().isEmpty()
                                    ) {
                                        Toast.makeText(
                                            context,
                                            "Please enter all fields!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else if (!mobilNo.matches(Regex("^[0-9]{10}$"))) {
                                        Toast.makeText(
                                            context,
                                            "Please enter a valid 10-digit mobile number!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else if (!Patterns.EMAIL_ADDRESS.matcher(email.trim())
                                            .matches()
                                    ) {
                                        Toast.makeText(
                                            context,
                                            "Please Enter a valid email",
                                            Toast.LENGTH_SHORT
                                        )
                                    } else {
                                        viewModel.saveUserProfile(
                                            name = firstname,
                                            email = email,
                                            phone = mobilNo,
//                                            profilePicPath = ProfilePicPath
                                        )
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
