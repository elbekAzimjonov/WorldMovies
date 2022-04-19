package com.elbek.worldmovies.presentation.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.elbek.worldmovies.data.models.Movies


@Database(entities = [Movies::class], version = MoviesDatabase.VERSION, exportSchema = false)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    companion object {
        const val VERSION = 1
    }
}
