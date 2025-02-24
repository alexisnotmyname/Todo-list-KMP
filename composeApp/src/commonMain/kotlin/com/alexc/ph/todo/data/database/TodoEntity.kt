package com.alexc.ph.todo.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val isDone: Boolean = false,
    val order: Int,
    val dateTimeCreated: Long? = null,
    val dateTimeDue: Long? = null,
    val dateTimeCompleted: Long? = null,
)

