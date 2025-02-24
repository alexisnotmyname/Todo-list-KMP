package com.alexc.ph.todo.presentation

import com.alexc.ph.todo.domain.TodoItem

sealed interface TodoListAction {
    data class OnAddTodo(val todo: TodoItem) : TodoListAction
    data class OnEditTodo(val todo: TodoItem) : TodoListAction
    data class OnToggleTodo(val todo: TodoItem) : TodoListAction
    data class OnRemoveTodo(val todo: TodoItem) : TodoListAction
    data class OnTodoItemDragged(val fromIndex: Int, val toIndex: Int) : TodoListAction
    data class OnStoppedEditing(val todo: TodoItem, val newTitle: String) : TodoListAction
    data object OnTodoItemDraggedEnd : TodoListAction
}
