package com.practice.daily_task.todoUI

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.practice.daily_task.R
import kotlin.math.exp

@Composable
fun PriorityChip(
    selectedPriority : Priority,
    onPrioritySelected : (Priority) -> Unit,
    enabled : Boolean = true,

) {
    var expanded by remember { mutableStateOf(false) }

    Box{
        AssistChip(
            onClick = {
                if(enabled){
                expanded = true
                }},
            label = { Text(text = selectedPriority.label) },
            leadingIcon = {
                Icon(
                    painter = painterResource(id=R.drawable.flag_icon),
                    contentDescription = null,
                    modifier = Modifier.size(AssistChipDefaults.IconSize),
                    tint = selectedPriority.color
                )
            }
        )
        DropdownMenu(
            expanded=expanded,
            onDismissRequest = { expanded = false }
        ) {
            Priority.values().filter{it != Priority.None}.forEach { priority ->
                DropdownMenuItem(
                    text = {Text(priority.label)},
                    onClick = {
                        if(enabled) {
                        onPrioritySelected(priority)
                        expanded = false
                    }
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id=R.drawable.flag_icon),
                            contentDescription = null,
                            modifier = Modifier.size(AssistChipDefaults.IconSize),
                            tint = priority.color
                        )
                    }
                )
            }

        }
    }
}