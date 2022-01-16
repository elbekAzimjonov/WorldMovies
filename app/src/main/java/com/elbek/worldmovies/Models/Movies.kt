package com.elbek.worldmovies.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "Worldmovies")
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