package com.elbek.worldmovies.presentation.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.elbek.worldmovies.data.models.Movies

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movies: Movies)

    @Query("SELECT * FROM worldMovies ORDER BY id DESC")
    fun getAllMoviesDb(): LiveData<List<Movies>>

    @Delete
    fun deleteMovies(movies: Movies)
}