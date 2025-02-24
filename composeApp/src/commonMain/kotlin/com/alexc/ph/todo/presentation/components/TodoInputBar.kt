package com.alexc.ph.todo.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import todoappkmp.composeapp.generated.resources.Res
import todoappkmp.composeapp.generated.resources.todo_hint


@Composable
fun TodoInputBar(
    modifier: Modifier = Modifier,
    onAddButtonClick: (String) -> Unit = {}
) {
    var input by rememberSaveable { mutableStateOf("") }
    var enableAddButton by rememberSaveable { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(size = 4.dp),
        modifier = modifier
            .padding(8.dp)
            .height(64.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
    ){
        Row(
            modifier = modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.primaryContainer),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BasicTextField(
                modifier = Modifier.weight(1f),
                value = input,
                onValueChanged = { newText ->
                    input = newText
                    enableAddButton = input.isNotEmpty()
                },
                placeholder = {
                    Text(
                        text = stringResource(Res.string.todo_hint),
                        style = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.primary),
                    )
                },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                textDecoration = null,
                textColor = MaterialTheme.colorScheme.primary,
                textStyle = MaterialTheme.typography.labelMedium,
                )
            val fabColor = MaterialTheme.colorScheme.onPrimary
            val iconTint = MaterialTheme.colorScheme.primary
            val addFabColor = if (enableAddButton) fabColor else fabColor.copy(alpha = 0.5f)
            val addIconTint = if (enableAddButton) iconTint else iconTint.copy(alpha = 0.5f)

            IconButton(
                enabled = enableAddButton,
                colors = IconButtonDefaults.iconButtonColors().copy(
                    containerColor = addFabColor,
                ),
                onClick = {
                    onAddButtonClick(input)
                    input = ""
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = addIconTint
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}
