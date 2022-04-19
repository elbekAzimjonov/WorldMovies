package com.elbek.worldmovies.presentation.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.elbek.worldmovies.data.models.Movies

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movies: Movies)

    @Query("SELECT * FROM worldMovies ORDER BY id DESC")
    fun getAllMovies(): LiveData<List<Movies>>

    @Delete
    suspend fun deleteMovies(movies: Movies)

}