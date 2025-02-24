package com.alexc.ph.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.alexc.ph.todo.data.database.DatabaseFactory
import com.alexc.ph.todo.data.database.TodoListDatabase
import com.alexc.ph.todo.data.repository.DefaultTodoListRepository
import com.alexc.ph.todo.domain.TodoListRepository
import com.alexc.ph.todo.presentation.TodoListViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {

    singleOf(::DefaultTodoListRepository).bind<TodoListRepository>()

    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single { get<TodoListDatabase>().todoListDao }

    viewModelOf(::TodoListViewModel)
}