package com.alexc.ph.todo.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.alexc.ph.core.presentation.millisToDateFormat


@Composable
fun DateAssistChip(
    date: Long,
    onDateSelected: (Long) -> Unit = {},
) {
    var selectedDate by remember { mutableLongStateOf(date) }
    var showModal by remember { mutableStateOf(false) }

    AssistChip(
        onClick = { showModal = true },
        label = { Text(selectedDate.millisToDateFormat()) },
        leadingIcon = {
            Icon(
                Icons.Filled.DateRange,
                contentDescription = "date",
                Modifier.size(AssistChipDefaults.IconSize)
            )
        }
    )

    if (showModal) {
        DatePickerModal(
            date = selectedDate,
            onDateSelected = { selectedDateMillis ->
                selectedDateMillis?.let {
                    selectedDate = it
                    onDateSelected(it)
                }
             },
            onDismiss = { showModal = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    date: Long? = null,
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = date
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
    ) {
        DatePicker(state = datePickerState)
    }
}