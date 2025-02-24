package com.alexc.ph.todo.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.alexc.ph.core.presentation.components.GenericTopAppBar
import com.alexc.ph.core.presentation.getCurrentDateMillis
import org.jetbrains.compose.resources.stringResource
import todoappkmp.composeapp.generated.resources.Res
import todoappkmp.composeapp.generated.resources.add_new_task
import todoappkmp.composeapp.generated.resources.back
import todoappkmp.composeapp.generated.resources.task_name

@Composable
fun NewTaskScreen(
    value: String = "",
    title: String = "",
    navigateBack: () -> Unit,
    onSaveClick: (String, Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    var input by remember { mutableStateOf(value) }
    val dateNow = getCurrentDateMillis()
    println("ALEX dateNow: ${dateNow}")
    var selectedDate by remember { mutableLongStateOf(dateNow) }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            GenericTopAppBar(
                title = title,
                navigation = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBackIosNew,
                            contentDescription = stringResource(Res.string.back)
                        )
                    }
                },
                action = {
                    IconButton(onClick = { onSaveClick(input, selectedDate) }) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                        )
                    }
                }
            )

            BasicTextField(
                value = input,
                placeholder = {
                    Text(
                        text = stringResource(Res.string.task_name),
                        style = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.primary),
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                textColor = MaterialTheme.colorScheme.primary,
                textStyle = MaterialTheme.typography.bodyMedium,
                textDecoration = null,
                onValueChanged = {
                    input = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
            )

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                DateAssistChip(
                    date = dateNow,
                    onDateSelected = {
                        selectedDate = it
                    }
                )
            }
        }

        FloatingActionButton(
            onClick = {
                onSaveClick(input, selectedDate)
            },
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
            )
        }
    }
}

