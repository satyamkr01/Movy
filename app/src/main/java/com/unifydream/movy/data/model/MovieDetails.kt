package com.unifydream.movy.data.model

data class MovieDetails(
    val title: String,
    val overview: String,
    val vote_average: Float,
    val release_date: String,
    val poster_path: String,
    val genres: List<Genre>
)

data class Genre(val name: String)