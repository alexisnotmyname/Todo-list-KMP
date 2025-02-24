package com.alexc.ph

import android.app.Application
import com.alexc.ph.di.initKoin
import org.koin.android.ext.koin.androidContext

class TodoListApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@TodoListApplication)
        }
    }
}