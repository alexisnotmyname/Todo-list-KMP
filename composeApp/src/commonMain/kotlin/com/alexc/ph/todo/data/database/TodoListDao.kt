package com.alexc.ph.todo.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoListDao {
    @Query("SELECT * FROM todoentity order by `isDone` ASC, `order` ASC")
    fun getAllTodoList(): Flow<List<TodoEntity>>

    @Insert
    suspend fun insertTodo(todoEntity: TodoEntity)

    @Upsert
    suspend fun upsert(todoEntity: TodoEntity)

    @Update
    suspend fun updateTodo(todoEntity: TodoEntity)

    @Update
    suspend fun updateTodoItems(todos: List<TodoEntity>)

    @Delete
    suspend fun deleteTodo(todoEntity: TodoEntity)
}