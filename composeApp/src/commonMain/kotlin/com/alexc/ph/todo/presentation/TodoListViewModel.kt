package com.alexc.ph.todo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexc.ph.todo.domain.TodoListRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TodoListViewModel(
    private val todoListRepository: TodoListRepository
): ViewModel() {

    private val _state = MutableStateFlow(TodoListState())
    val state = _state
        .onStart { getTodoList() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    private fun getTodoList() {
        todoListRepository.myTodoList
            .onEach { todoList ->
                _state.update {
                    it.copy(todoList = todoList)
                }
            }
            .launchIn(viewModelScope)

    }

    fun onAction(action: TodoListAction) {
        when(action) {
            is TodoListAction.OnAddTodo -> {
                val newTodo = action.todo.copy(order = _state.value.todoList.lastIndex + 1)
                viewModelScope.launch {
                    todoListRepository.add(newTodo)
                }
            }
            is TodoListAction.OnRemoveTodo -> {
                viewModelScope.launch {
                    todoListRepository.delete(action.todo)
                }
            }
            is TodoListAction.OnToggleTodo -> {
                viewModelScope.launch {
                    val updated = action.todo.copy(isDone = !action.todo.isDone)
                    todoListRepository.update(updated)
                }
            }
            is TodoListAction.OnStoppedEditing -> {
                viewModelScope.launch {
                    val updated = action.todo.copy(title = action.newTitle)
                    todoListRepository.update(updated)
                }
            }
            is TodoListAction.OnTodoItemDragged -> {
                val todoList = _state.value.todoList
                if(todoList[action.fromIndex].isDone || todoList.size <= 1) return
                val updatedList = todoList.toMutableList().apply {
                    add(action.toIndex, removeAt(action.fromIndex))
                }.mapIndexed { index, todoItem ->
                    todoItem.copy(order = index)
                }

                _state.update {
                    it.copy(
                        todoList = updatedList
                    )
                }
            }
            TodoListAction.OnTodoItemDraggedEnd -> {
                viewModelScope.launch {
                    todoListRepository.updateTodoItems(_state.value.todoList)
                }
            }
            is TodoListAction.OnEditTodo -> {
                viewModelScope.launch {
                    todoListRepository.update(action.todo)
                    _state.update {
                        it.copy(
                            todoList = it.todoList
                        )
                    }
                }
            }
        }
    }
}