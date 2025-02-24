package com.alexc.ph.todo.data.database

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object TodoListDatabaseConstructor: RoomDatabaseConstructor<TodoListDatabase> {
    override fun initialize(): TodoListDatabase
}