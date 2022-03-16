package com.elbek.worldmovies.retrofit

import com.elbek.worldmovies.api.MovieApi
import com.elbek.worldmovies.api.VideoApi
import com.elbek.worldmovies.api.castApi.CastActors
import retrofit2.Call
import retrofit2.http.*

interface ApiRequest {

    @GET("discover/movie?api_key=fab3626d9e8d767dbf679f3c64849db5&append_to_response=videos&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=True&page=1")
    fun getMoviesApi(): Call<MovieApi>

    @GET("discover/movie?api_key=fab3626d9e8d767dbf679f3c64849db5&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=True&page=2")
    fun getPopularMovie(): Call<MovieApi>

    @GET("movie/{key}?api_key=fab3626d9e8d767dbf679f3c64849db5&append_to_response=credits")
    fun getActors(
        @Path("key") end: Int
    ): Call<CastActors>

    @GET("movie/{apiKey}/videos?api_key=fab3626d9e8d767dbf679f3c64849db5")
    fun getMovieKey(
        @Path("apiKey") key: Int
    ): Call<VideoApi>
}