package com.alexc.ph.todo.domain

data class TodoItem(
    val id: Int = 0,
    val title: String = "",
    val isDone: Boolean = false,
    val order: Int = 0,
    val dateTimeCreated: Long? = null,
    val dateTimeDue: Long? = null,
    val dateTimeCompleted: Long? = null
)
