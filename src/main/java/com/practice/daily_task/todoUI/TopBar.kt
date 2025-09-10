package com.practice.daily_task.todoUI


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.HorizontalDivider
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
    title:String
) {
    Column (
            modifier = Modifier.fillMaxWidth()
                .height(100.dp)
                .statusBarsPadding()
                .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
                Text(
                    text = title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 12.dp, top = 4.dp)
                )
        Spacer(modifier=Modifier.height(20.dp))
        HorizontalDivider(color = Color.Gray)
    }
}



