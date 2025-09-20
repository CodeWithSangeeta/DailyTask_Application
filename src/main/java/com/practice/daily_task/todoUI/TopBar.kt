package com.practice.daily_task.todoUI


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun TopBar(
    title:String,
    showBackButton : Boolean = true,
    onBackClick : () -> Unit ={}
) {
    Column (
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .statusBarsPadding()
                .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)) {
            if(showBackButton) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.align(Alignment.BottomStart)
                      //  .padding(bottom = 2.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .size(32.dp)
                    )
                }
            }
            Text(
                text = title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(start = 12.dp, top = 4.dp)
                    .align(Alignment.Center)
            )
        }
        Spacer(modifier=Modifier.height(34.dp))
        HorizontalDivider(color = MaterialTheme.colorScheme.outline)

    }
}



