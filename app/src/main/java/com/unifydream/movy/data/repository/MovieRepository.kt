package com.unifydream.movy.data.repository

import com.unifydream.movy.data.db.FavoriteMovieEntity
import com.unifydream.movy.data.model.Movie
import com.unifydream.movy.data.model.MovieDetails

interface MovieRepository {
    suspend fun searchMovies(query: String): Result<List<Movie>>
    suspend fun getMovieDetails(id: Int): Result<MovieDetails>
    suspend fun getFavoriteMovies(): List<FavoriteMovieEntity>
    suspend fun addFavorite(movie: FavoriteMovieEntity)
    suspend fun removeFavorite(movie: FavoriteMovieEntity)
}