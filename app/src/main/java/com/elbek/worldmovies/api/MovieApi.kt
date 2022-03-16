package com.elbek.worldmovies.api

data class MovieApi(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)