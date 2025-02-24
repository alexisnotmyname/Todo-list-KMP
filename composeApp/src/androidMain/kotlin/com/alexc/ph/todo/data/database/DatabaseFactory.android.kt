package com.alexc.ph.todo.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class DatabaseFactory(
    private val context: Context
) {
    actual fun create(): RoomDatabase.Builder<TodoListDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(TodoListDatabase.DATABASE_NAME)
        return Room.databaseBuilder(
            context = appContext,
            name = dbFile.absolutePath
        )
    }
}