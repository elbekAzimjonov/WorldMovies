package com.elbek.worldmovies.presentation.di

import android.app.Application
import com.elbek.worldmovies.presentation.di.component.ApplicationComponent
import com.elbek.worldmovies.presentation.di.component.DaggerApplicationComponent
import com.elbek.worldmovies.presentation.di.module.AppModule
import com.elbek.worldmovies.presentation.di.module.DatabaseModule
import com.elbek.worldmovies.presentation.di.module.NetworkModule

@Suppress("DEPRECATION")
class App : Application() {
 lateinit var applicationComponent: ApplicationComponent
    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent
            .builder()
            .networkModule(NetworkModule())
            .appModule(AppModule(applicationContext))
            .databaseModule(DatabaseModule())
            .build()
    }
}