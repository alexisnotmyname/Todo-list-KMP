package com.alexc.ph

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.alexc.ph.todo.domain.TodoItem
import com.alexc.ph.todo.presentation.TodoListScreen
import com.alexc.ph.todo.presentation.TodoListState

@Preview(showBackground = true)
@Composable
private fun TodoListScreenPreview() {
    TodoListScreen(
        state = TodoListState(
            todoList = todoList
        ),
        onAction = {}
    )
}

val todoList = listOf(
    TodoItem(1, "Learn Compose", false, 1),
    TodoItem(2, "Learn Room", false, 2),
    TodoItem(3, "Learn Kotlin", true, 3)
)