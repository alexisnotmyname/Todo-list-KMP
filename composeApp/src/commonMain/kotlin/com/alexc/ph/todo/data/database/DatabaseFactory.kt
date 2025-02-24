package com.alexc.ph.todo.data.database

import androidx.room.RoomDatabase

expect class DatabaseFactory {
    fun create(): RoomDatabase.Builder<TodoListDatabase>
}