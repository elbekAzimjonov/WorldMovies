package com.elbek.worldmovies.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elbek.worldmovies.api.MovieApi
import com.elbek.worldmovies.api.VideoApi
import com.elbek.worldmovies.api.castApi.CastActors
import com.elbek.worldmovies.models.Movies
import com.elbek.worldmovies.repository.DatabaseRepository

import com.elbek.worldmovies.repository.MovieRepository

class MovieViewModel : ViewModel() {
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