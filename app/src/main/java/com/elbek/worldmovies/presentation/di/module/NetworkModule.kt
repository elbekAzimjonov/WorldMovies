package com.elbek.worldmovies.presentation.di.module

import com.elbek.worldmovies.presentation.network.ApiRequest
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule() {
    @Singleton
    @Provides
    fun getRetrofit(): ApiRequest {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequest::class.java)
    }

}
