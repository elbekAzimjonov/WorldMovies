package com.elbek.worldmovies.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "worldMovies")
class Movies(
    var moviesId: Int,
    var moviesImage: String,
    var moviesName: String,
    var moviesDescription: String,
    var moviesRating: String

) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}