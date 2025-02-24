package com.alexc.ph.todo.domain

import kotlinx.coroutines.flow.Flow

interface TodoListRepository {
    val myTodoList: Flow<List<TodoItem>>
    suspend fun add(todoItem: TodoItem)
    suspend fun update(todoItem: TodoItem)
    suspend fun updateTodoItems(todoList: List<TodoItem>)
    suspend fun delete(todoItem: TodoItem)

}