package com.alexc.ph.todo.data.mappers

import com.alexc.ph.todo.data.database.TodoEntity
import com.alexc.ph.todo.domain.TodoItem

fun TodoItem.toTodoEntity() = TodoEntity(
    id = id,
    title = title,
    isDone = isDone,
    order = order,
    dateTimeCreated = dateTimeCreated,
    dateTimeDue = dateTimeDue,
    dateTimeCompleted = dateTimeCompleted
)
fun TodoEntity.toTodoItem() = TodoItem(
    id = id,
    title = title,
    isDone = isDone,
    order = order,
    dateTimeCreated = dateTimeCreated,
    dateTimeDue = dateTimeDue,
    dateTimeCompleted = dateTimeCompleted
)