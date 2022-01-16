package com.elbek.worldmovies.ViewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Database
import com.elbek.worldmovies.Api.MovieApi
import com.elbek.worldmovies.Api.VideoApi
import com.elbek.worldmovies.Api.castApi.CastActors
import com.elbek.worldmovies.Models.Movies
import com.elbek.worldmovies.Reapository.DatabaseRepository

import com.elbek.worldmovies.Reapository.MovieRepository

class MovieViewModel() : ViewModel() {
    private var videoLiveData: MutableLiveData<VideoApi> = MutableLiveData()
    fun initDatabase(context: Context) {
        DatabaseRepository.initDatabase(context)
    }

    fun getDbViewModel(): LiveData<List<Movies>> {

        return DatabaseRepository.getAllMovie()
    }

    fun getVideVideModel(movieId: Int): LiveData<VideoApi> {
        videoLiveData = MovieRepository.getVideoLiveData(movieId)
        return videoLiveData
    }

    fun getVideoClear(): Unit {

        return videoLiveData.postValue(null)
    }

    fun getTopViewModel(): LiveData<MovieApi> {
        return MovieRepository.getTopLiveData()
    }

    fun getPopularViewModel(): LiveData<MovieApi> {
        return MovieRepository.getPopularLiveData()
    }

    fun getActorsViewModel(movieId: Int): LiveData<CastActors> {
        return MovieRepository.getActorsLiveData(movieId)
    }

    fun insertMovies(movies: Movies) {
        DatabaseRepository.insertMovie(movies)
    }
}