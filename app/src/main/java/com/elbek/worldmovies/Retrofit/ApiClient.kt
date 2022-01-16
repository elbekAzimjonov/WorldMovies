package com.elbek.worldmovies.Retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    val BASEURL = "https://api.themoviedb.org/3/"
    fun getRetrofit(): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }
}