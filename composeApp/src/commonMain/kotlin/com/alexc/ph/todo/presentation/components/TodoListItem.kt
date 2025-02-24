package com.alexc.ph.todo.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DragIndicator
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alexc.ph.core.presentation.getCurrentDateMillis
import com.alexc.ph.core.presentation.millisToDateFormat
import com.alexc.ph.todo.domain.TodoItem
import org.jetbrains.compose.resources.stringResource
import todoappkmp.composeapp.generated.resources.Res
import todoappkmp.composeapp.generated.resources.task_name


@Composable
fun TodoListItem(
    modifier: Modifier = Modifier,
    item: TodoItem,
    elevation: Dp = 1.dp,
    onCheckedChanged: (TodoItem) -> Unit,
    onStoppedEditing: (TodoItem, String) -> Unit,
    onItemDelete: (TodoItem) -> Unit,
    onMoreClicked: (TodoItem) -> Unit,
) {

    var text by remember { mutableStateOf(item.title) }
    LaunchedEffect(item) {
        text = item.title
    }

    var isFocused by remember { mutableStateOf(false) }
    DisposableEffect(Unit) {
        onDispose {
            if(isFocused) {
                onStoppedEditing(item, text)
            }
        }
    }

    val focusRequester = remember { FocusRequester() }

    val containerColor = MaterialTheme.colorScheme.onSecondary
    val contentColor = MaterialTheme.colorScheme.primary
    val secondaryContentColor = MaterialTheme.colorScheme.secondary

    val backgroundColor = if (item.isDone) containerColor.copy(alpha = 0.5f) else containerColor
    val textColor = if (item.isDone) contentColor.copy(alpha = 0.5f) else contentColor
    val textDecoration = if (item.isDone) TextDecoration.LineThrough else null
    val iconColorFilter = if (item.isDone) ColorFilter.tint(contentColor.copy(alpha = 0.5f)) else ColorFilter.tint(contentColor)
    val iconTintColor = if (item.isDone) contentColor.copy(alpha = 0.5f) else contentColor

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(elevation),
        shape = shapes.extraSmall
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(backgroundColor),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                imageVector = Icons.Default.DragIndicator,
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 8.dp),
                colorFilter = iconColorFilter
            )

            Checkbox(
                checked = item.isDone,
                onCheckedChange = {
                    onCheckedChanged(item)
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = contentColor.copy(alpha = 0.5f),
                    uncheckedColor = contentColor,
                    checkmarkColor = Color.White
                ),
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 4.dp)
            ) {
                BasicTextField(
                    value = text,
                    placeholder = {
                        Text(
                            text = stringResource(Res.string.task_name),
                            style = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.primary),
                        )
                    },
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .focusRequester(focusRequester)
                        .onFocusChanged { state ->
                            isFocused = state.isFocused
                            if (!state.isFocused) {
                                onStoppedEditing(item, text)
                            }
                        },
                    enabled = !item.isDone,
                    textDecoration = textDecoration,
                    textColor = textColor,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    colors = TextFieldDefaults.colors().copy(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    onValueChanged = {
                        text = it
                    },
                    onStoppedEditing = {
                        onStoppedEditing(item, text)
                    }
                )

                item.dateTimeDue?.let {
                    val isOverdue = it < getCurrentDateMillis()
                    val textColorDate = when {
                        item.isDone -> secondaryContentColor.copy(alpha = 0.5f)
                        isOverdue -> Color.Red
                        else -> secondaryContentColor
                    }

                    Text(
                        text = it.millisToDateFormat(),
                        fontWeight = FontWeight.ExtraBold,
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.ExtraBold),
                        color = textColorDate,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(start = 16.dp)
                    )
                }
            }

            Column(
                modifier = Modifier.padding(end = 8.dp)
            ) {
                if(isFocused || item.isDone) {
                    IconButton(
                        onClick = { onItemDelete(item) },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = iconTintColor
                        )
                    }
                } else {
                    IconButton(
                        onClick = { onMoreClicked(item) },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = null,
                            tint = iconTintColor
                        )
                    }
                }
            }
        }
    }
}

