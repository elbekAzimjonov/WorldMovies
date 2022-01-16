package com.elbek.worldmovies.Reapository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.elbek.worldmovies.Api.MovieApi
import com.elbek.worldmovies.Models.Movies
import com.elbek.worldmovies.db.MoviesDatabase
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