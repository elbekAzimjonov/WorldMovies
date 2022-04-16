package com.elbek.worldmovies.presentation.network

import com.elbek.worldmovies.data.models.MovieApi
import com.elbek.worldmovies.data.models.VideoApi
import com.elbek.worldmovies.data.models.castApi.CastActors
import retrofit2.http.*

interface ApiRequest {

    @GET("discover/movie?api_key=fab3626d9e8d767dbf679f3c64849db5&append_to_response=videos&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=True&page=1")
    suspend fun getMoviesApi(): MovieApi

    @GET("discover/movie?api_key=fab3626d9e8d767dbf679f3c64849db5&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=True&page=2")
    suspend fun getPopularMovie(): MovieApi

    @GET("movie/{key}?api_key=fab3626d9e8d767dbf679f3c64849db5&append_to_response=credits")
    suspend fun getActors(
        @Path("key") end: Int
    ): CastActors

    @GET("movie/{apiKey}/videos?api_key=fab3626d9e8d767dbf679f3c64849db5")
    suspend fun getMovieKey(
        @Path("apiKey") key: Int
    ): VideoApi
}