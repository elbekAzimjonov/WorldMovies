package com.elbek.worldmovies.presentation.di.module

import android.content.Context
import androidx.room.Room
import com.elbek.worldmovies.presentation.db.MoviesDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun getDatabaseInstance(context: Context): MoviesDatabase {
        return Room.databaseBuilder(
            context.applicationContext, MoviesDatabase::class.java, "movies_db"
        ).fallbackToDestructiveMigration()
            .build()
    }
    @Singleton
    @Provides
    fun provideYourDao(db: MoviesDatabase) = db.userDao()
}