package com.elbek.worldmovies.presentation.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.elbek.worldmovies.data.models.MovieApi
import com.elbek.worldmovies.data.models.VideoApi
import com.elbek.worldmovies.data.models.castApi.CastActors
import com.elbek.worldmovies.presentation.network.MovieDataSource
import com.elbek.worldmovies.data.domain.Resource
import com.elbek.worldmovies.data.models.Movies
import kotlinx.coroutines.*
import javax.inject.Inject

@Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
@OptIn(DelicateCoroutinesApi::class)
class MovieRepository @Inject constructor(private val movieDataSource: MovieDataSource) {
    val movies = MutableLiveData<List<Movies>>()
    //RoomDatabase
    fun insertMovie(movies: Movies) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                movieDataSource.insertMovie(movies)
            } catch (e: Exception) {

            }
        }
    }

    fun getAllMoviesDb():LiveData<List<Movies>>{
        return movieDataSource.getAllMoviesDb()
    }

    fun getTopLiveData(): MutableLiveData<Resource<MovieApi>> {
        GlobalScope.launch {
            topMovieLiveData.postValue(Resource.loading(null))
            coroutineScope {
                try {
                    val async = async { movieDataSource.getTopMovie() }
                    val movieList = async.await()
                    topMovieLiveData.postValue(Resource.success(movieList))
                } catch (e: Exception) {
                    topMovieLiveData.postValue(Resource.error(e.message.toString(), null))
                }
            }
        }
        return topMovieLiveData
    }

    fun getVideoLiveData(movieId: Int): MutableLiveData<Resource<VideoApi>> {
        GlobalScope.launch {
            videoLiveData.postValue(Resource.loading(null))
            coroutineScope {
                try {
                    val async = async { movieDataSource.getVideVideo(movieId) }
                    val videApi = async.await()
                    videoLiveData.postValue(Resource.success(videApi))
                } catch (e: Exception) {
                    videoLiveData.postValue(
                        Resource.error(
                            e.message.toString(), null
                        )
                    )
                }
            }

        }
        return videoLiveData
    }

    fun getPopularLiveData(): MutableLiveData<Resource<MovieApi>> {
        GlobalScope.launch {
            popularLiveData.postValue(Resource.loading(null))
            coroutineScope {
                try {
                    val async = async { movieDataSource.getPopularMovie() }
                    val movieList = async.await()
                    popularLiveData.postValue(Resource.success(movieList))
                } catch (e: Exception) {
                    popularLiveData.postValue(Resource.error(e.message.toString(), null))
                }

            }
        }
        return popularLiveData
    }

    fun getAllActor(key: Int): MutableLiveData<Resource<CastActors>> {
        GlobalScope.launch {
            actorsLiveData.postValue(Resource.loading(null))
            coroutineScope {
                try {
                    val async = async { movieDataSource.getAllActor(key) }
                    val actorList = async.await()
                    actorsLiveData.postValue(Resource.success(actorList))
                } catch (e: Exception) {
                    actorsLiveData.postValue(
                        Resource.error(
                            e.message.toString(), null
                        )
                    )
                }
            }
        }
        return actorsLiveData
    }

    companion object {
        private val topMovieLiveData = MutableLiveData<Resource<MovieApi>>()
        private var popularLiveData = MutableLiveData<Resource<MovieApi>>()
        private var videoLiveData = MutableLiveData<Resource<VideoApi>>()
        private var actorsLiveData = MutableLiveData<Resource<CastActors>>()
    }
}