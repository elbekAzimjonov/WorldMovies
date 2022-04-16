package com.elbek.worldmovies.data.models

data class MovieApi(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)