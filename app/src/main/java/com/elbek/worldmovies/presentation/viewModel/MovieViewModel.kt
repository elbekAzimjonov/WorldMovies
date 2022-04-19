package com.elbek.worldmovies.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.elbek.worldmovies.data.models.MovieApi
import com.elbek.worldmovies.data.models.VideoApi
import com.elbek.worldmovies.data.models.castApi.CastActors
import com.elbek.worldmovies.data.domain.Resource
import com.elbek.worldmovies.data.models.Movies
import com.elbek.worldmovies.presentation.repository.MovieRepository
import javax.inject.Inject

class MovieViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    fun insertMovie(movies: Movies) {
        repository.insertMovie(movies)
    }

    fun getAllMoviesDb():LiveData<List<Movies>>{
       return repository.getAllMoviesDb()
    }
    fun getVideoLiveData(movieId: Int): LiveData<Resource<VideoApi>> {
        return repository.getVideoLiveData(movieId)
    }

    fun getPopularLiveData(): LiveData<Resource<MovieApi>> {
        return repository.getPopularLiveData()
    }

    fun getTopViewModel(): LiveData<Resource<MovieApi>> {
        return repository.getTopLiveData()
    }

    fun getAllActor(key: Int): LiveData<Resource<CastActors>> {
        return repository.getAllActor(key)
    }
}