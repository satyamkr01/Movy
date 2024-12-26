package com.unifydream.movy.data.model

data class MovieResponse(val results: List<Movie>)

data class Movie(
    val id: Int,
    val title: String,
    val poster_path: String?,
    val release_date: String?
)