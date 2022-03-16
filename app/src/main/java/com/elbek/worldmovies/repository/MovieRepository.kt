package com.elbek.worldmovies.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.elbek.worldmovies.api.MovieApi
import com.elbek.worldmovies.api.VideoApi
import com.elbek.worldmovies.api.castApi.CastActors
import com.elbek.worldmovies.retrofit.ApiClient
import com.elbek.worldmovies.retrofit.ApiRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepository {
    companion object {
        private var topMovieLiveData: MutableLiveData<MovieApi> = MutableLiveData()
        private var popularLiveData: MutableLiveData<MovieApi> = MutableLiveData()
        private var actorsLiveData: MutableLiveData<CastActors> = MutableLiveData()
        private var videoLiveData: MutableLiveData<VideoApi> = MutableLiveData()
        val apiClient = ApiClient()
        val retrofit = apiClient.getRetrofit()
        val apiService = retrofit.create(ApiRequest::class.java)
        fun getVideoLiveData(movieId: Int): MutableLiveData<VideoApi> {
            CoroutineScope(Dispatchers.IO).launch {
                apiService.getMovieKey(movieId).enqueue(object : Callback<VideoApi> {
                    override fun onResponse(call: Call<VideoApi>, response: Response<VideoApi>) {
                        if (response.isSuccessful) {
                            val videApi = response.body()
                            videoLiveData.postValue(videApi)
                        }
                    }

                    override fun onFailure(call: Call<VideoApi>, t: Throwable) {

                    }
                })
            }
            return videoLiveData
        }

        fun getTopLiveData(): MutableLiveData<MovieApi> {
            CoroutineScope(Dispatchers.IO).launch {
                apiService.getMoviesApi().enqueue(object : Callback<MovieApi> {
                    override fun onResponse(call: Call<MovieApi>, response: Response<MovieApi>) {
                        if (response.isSuccessful) {
                            val data = response.body()
                            topMovieLiveData.postValue(data)
                        }
                    }

                    override fun onFailure(call: Call<MovieApi>, t: Throwable) {

                    }
                })
            }
            return topMovieLiveData
        }

        fun getPopularLiveData(): MutableLiveData<MovieApi> {
            CoroutineScope(Dispatchers.IO).launch {
                apiService.getPopularMovie().enqueue(object : Callback<MovieApi> {
                    override fun onResponse(call: Call<MovieApi>, response: Response<MovieApi>) {
                        if (response.isSuccessful) {
                            val data = response.body()
                            popularLiveData.postValue(data)
                            Log.v("RequestApi", "${data}")
                        }
                    }

                    override fun onFailure(call: Call<MovieApi>, t: Throwable) {
                    }
                })
            }
            return popularLiveData
        }

        fun getActorsLiveData(movieId: Int): MutableLiveData<CastActors> {
            CoroutineScope(Dispatchers.IO).launch {
                apiService.getActors(movieId).enqueue(object : Callback<CastActors> {
                    override fun onResponse(
                        call: Call<CastActors>,
                        response: Response<CastActors>
                    ) {
                        if (response.isSuccessful) {
                            val actorsData = response.body()
                            actorsLiveData.postValue(actorsData)
                        }
                    }

                    override fun onFailure(call: Call<CastActors>, t: Throwable) {

                    }

                })
            }
            return actorsLiveData
        }

    }
}