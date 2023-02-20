package com.example.yts

import android.app.Application
import com.example.yts.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@MyApp)
            modules(roomModule, networkModule, apiServiceModule, repoModule, viewModelModule)
        }
    }
}