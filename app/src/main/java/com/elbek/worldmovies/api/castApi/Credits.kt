package com.elbek.worldmovies.api.castApi

data class Credits(
    val cast: List<Cast>,
    val crew: List<Crew>
)