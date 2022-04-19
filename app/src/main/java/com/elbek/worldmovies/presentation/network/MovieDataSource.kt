package com.elbek.worldmovies.presentation.network


import androidx.lifecycle.LiveData
import com.elbek.worldmovies.data.models.MovieApi
import com.elbek.worldmovies.data.models.Movies
import com.elbek.worldmovies.data.models.VideoApi
import com.elbek.worldmovies.data.models.castApi.CastActors
import com.elbek.worldmovies.presentation.db.UserDao
import javax.inject.Inject

class MovieDataSource @Inject constructor(
    private val api: ApiRequest, private val userDao: UserDao
) {
    suspend fun getTopMovie(): MovieApi = api.getMoviesApi()

    suspend fun getPopularMovie(): MovieApi = api.getPopularMovie()

    suspend fun getVideVideo(key: Int): VideoApi = api.getMovieKey(key)

    suspend fun getAllActor(key: Int): CastActors = api.getActors(key)

    suspend fun insertMovie(movies: Movies) = userDao.insertMovie(movies)

     fun getAllMoviesDb(): LiveData<List<Movies>> = userDao.getAllMovies()
}