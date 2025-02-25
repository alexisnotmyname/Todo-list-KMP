package com.alexc.ph.todo.presentation

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alexc.ph.todo.domain.TodoItem
import com.alexc.ph.todo.presentation.components.DraggableItem
import com.alexc.ph.todo.presentation.components.NewTaskScreen
import com.alexc.ph.todo.presentation.components.TodoInputBar
import com.alexc.ph.todo.presentation.components.TodoListItem
import com.alexc.ph.todo.presentation.components.dragContainer
import com.alexc.ph.todo.presentation.components.rememberDragDropState
import org.jetbrains.compose.resources.stringResource
import todoappkmp.composeapp.generated.resources.Res
import todoappkmp.composeapp.generated.resources.add_new_task
import todoappkmp.composeapp.generated.resources.edit_task

@Composable
fun TodoListScreenRoot(
    viewModel: TodoListViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    TodoListScreen(
        modifier = modifier
            .fillMaxSize(),
        state = state,
        onAction = viewModel::onAction,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    modifier: Modifier = Modifier,
    state: TodoListState,
    onAction: (TodoListAction) -> Unit,
) {
    val todoList = state.todoList
    var showModalBottomSheet by rememberSaveable { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(TodoItem()) }
    var isEditTask by remember { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState()
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { focusManager.clearFocus() })
                }
        ) {

            val listState = rememberLazyListState()
            val dragDropState =
                rememberDragDropState(
                    lazyListState = listState,
                    onMove = { fromIndex, toIndex ->
                        onAction(TodoListAction.OnTodoItemDragged(fromIndex, toIndex))
                    },
                    onDragEnd = {
                        onAction(TodoListAction.OnTodoItemDraggedEnd)
                    }
                )

            LazyColumn(
                modifier = Modifier.dragContainer(dragDropState),
                state = listState,
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(todoList, key = { _, item -> item.id }) { index, item ->
                    DraggableItem(dragDropState, index) { isDragging ->
                        val elevation by animateDpAsState(if (isDragging) 4.dp else 1.dp, label = "")
                        TodoListItem(
                            modifier = Modifier.fillMaxWidth(),
                            item = item,
                            elevation = elevation,
                            onCheckedChanged = { todo ->
                                onAction(TodoListAction.OnToggleTodo(todo))
                            },
                            onStoppedEditing = { todo, newTitle ->
                                onAction(TodoListAction.OnStoppedEditing(todo, newTitle))
                            },
                            onItemDelete = { todo ->
                                onAction(TodoListAction.OnRemoveTodo(todo))
                            },
                            onMoreClicked = { todo ->
                                selectedItem = todo
                                showModalBottomSheet = true
                                isEditTask = true
                            }
                        )
                    }
                }
                item { Spacer(Modifier.height(64.dp)) }
            }

            TodoInputBar(
                modifier = Modifier.align(Alignment.BottomStart),
                onAddButtonClick = { todo ->
                    selectedItem = selectedItem.copy(title = todo)
                    showModalBottomSheet = true
                }
            )
        }
    }

    if(showModalBottomSheet) {
        ModalBottomSheet(
            modifier = Modifier,
            sheetState = sheetState,
            onDismissRequest = {
                showModalBottomSheet = false
                isEditTask = false
            }
        ) {
            NewTaskScreen(
                todoItem = if(isEditTask) selectedItem else null,
                title = if(isEditTask) stringResource(Res.string.edit_task) else stringResource(Res.string.add_new_task),
                navigateBack = {
                    showModalBottomSheet = false
                    isEditTask = false
                },
                onSaveClick = { todo, date ->
                    if(isEditTask) {
                        selectedItem = selectedItem.copy(
                            title = todo,
                            dateTimeDue = date
                        )
                        onAction(TodoListAction.OnEditTodo(selectedItem))
                        isEditTask = false
                    } else {
                        selectedItem = TodoItem(
                            title = todo,
                            dateTimeDue = date
                        )
                        onAction(TodoListAction.OnAddTodo(selectedItem))
                    }
                    showModalBottomSheet = false
                }
            )
        }
    }
}