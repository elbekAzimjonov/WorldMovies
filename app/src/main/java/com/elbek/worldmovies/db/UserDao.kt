package com.elbek.worldmovies.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.elbek.worldmovies.Api.Result
import com.elbek.worldmovies.Models.Movies

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movies: Movies)

    @Query("SELECT * FROM worldmovies ORDER BY id DESC")
    fun getAllMoviesDb(): LiveData<List<Movies>>

    @Delete
    fun deleteMovies(movies: Movies)
}