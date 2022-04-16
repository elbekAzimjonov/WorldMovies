package com.elbek.worldmovies.presentation.network


import com.elbek.worldmovies.data.models.MovieApi
import com.elbek.worldmovies.data.models.VideoApi
import com.elbek.worldmovies.data.models.castApi.CastActors
import javax.inject.Inject

class MovieDataSource @Inject constructor(
    val api: ApiRequest
) {

    suspend fun getTopMovie(): MovieApi = api.getMoviesApi()

    suspend fun getPopularMovie(): MovieApi = api.getPopularMovie()

    suspend fun getVideVideo(key: Int): VideoApi = api.getMovieKey(key)

    suspend fun getAllActor(key: Int): CastActors = api.getActors(key)
}