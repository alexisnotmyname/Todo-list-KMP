package com.alexc.ph.todo.presentation.components

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextDecoration

@Composable
fun BasicTextField(
    value: String,
    placeholder: @Composable () -> Unit = {},
    enabled: Boolean = true,
    singleLine: Boolean = false,
    textDecoration: TextDecoration?,
    textStyle: TextStyle,
    textColor: Color,
    colors: TextFieldColors,
    onValueChanged: (String) -> Unit = {},
    onStoppedEditing: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        placeholder = placeholder,
        textStyle = textStyle.copy(
            color = textColor,
            textDecoration = textDecoration
        ),
        enabled = enabled,
        singleLine = singleLine,
        onValueChange = {
            onValueChanged(it)
        },
        colors = colors,
        keyboardActions = KeyboardActions(
            onDone = {
                onStoppedEditing()
            }
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            capitalization = KeyboardCapitalization.Sentences
        ),
        modifier = modifier
    )
}
