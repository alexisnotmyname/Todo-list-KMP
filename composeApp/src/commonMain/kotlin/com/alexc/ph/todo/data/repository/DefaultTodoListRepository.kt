package com.alexc.ph.todo.data.repository

import com.alexc.ph.todo.data.database.TodoListDao
import com.alexc.ph.todo.data.mappers.toTodoEntity
import com.alexc.ph.todo.data.mappers.toTodoItem
import com.alexc.ph.todo.domain.TodoItem
import com.alexc.ph.todo.domain.TodoListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultTodoListRepository(
    private val todoListDao: TodoListDao
): TodoListRepository {

    override val myTodoList: Flow<List<TodoItem>> = todoListDao.getAllTodoList().map {
        it.map { todoEntity -> todoEntity.toTodoItem() }
    }

    override suspend fun add(todoItem: TodoItem) {
        todoListDao.insertTodo(todoItem.toTodoEntity())
    }

    override suspend fun update(todoItem: TodoItem) {
        todoListDao.updateTodo(todoItem.toTodoEntity())
    }

    override suspend fun updateTodoItems(todoList: List<TodoItem>) {
        todoListDao.updateTodoItems(todoList.map { it.toTodoEntity() })
    }

    override suspend fun delete(todoItem: TodoItem) {
        todoListDao.deleteTodo(todoItem.toTodoEntity())
    }

}