package com.elbek.worldmovies.db

import android.content.Context
import androidx.room.*
import com.elbek.worldmovies.models.Movies


@Database(entities = [Movies::class], version = 1, exportSchema = false)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    companion object {
        private const val DATABASE_NAME = "moviesw_db"
        fun getDatabaseInstance(context: Context): MoviesDatabase {
            return Room.databaseBuilder(
                context.applicationContext, MoviesDatabase::class.java, DATABASE_NAME
            ).fallbackToDestructiveMigration()
                .build()
        }
    }
}
