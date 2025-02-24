package com.alexc.ph.todo.presentation

import com.alexc.ph.todo.domain.TodoItem

data class TodoListState(
    val todoList: List<TodoItem> = emptyList()
)
