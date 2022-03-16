package com.elbek.worldmovies.presentation.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.elbek.worldmovies.data.models.Movies
import com.elbek.worldmovies.presentation.db.MoviesDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DatabaseRepository {
    companion object {
        private var movieDb: MoviesDatabase? = null
        fun initDatabase(context: Context) {
            movieDb = MoviesDatabase.getDatabaseInstance(context)
        }

        fun insertMovie(movies: Movies) {
            CoroutineScope(Dispatchers.IO).launch {
                movieDb?.userDao()?.insertMovie(movies)
            }
        }

        fun getAllMovie(): LiveData<List<Movies>> {

            return movieDb?.userDao()?.getAllMoviesDb()!!
        }
    }
}