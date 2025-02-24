package com.alexc.ph.todo.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TodoEntity::class],
    version = 1
)
@ConstructedBy(TodoListDatabaseConstructor::class)
abstract class TodoListDatabase: RoomDatabase() {
    abstract val todoListDao: TodoListDao

    companion object {
        const val DATABASE_NAME = "kmp_todo_list.db"
    }
}