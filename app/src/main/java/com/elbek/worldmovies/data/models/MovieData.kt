package com.elbek.worldmovies.data.models

data class MovieData (
    val title: String,
    val release_date: String,
    val vote_average: Double,
    val poster_path: String,
    val overview: String,
    val original_language: String,
)