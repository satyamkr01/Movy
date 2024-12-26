package com.unifydream.movy.data.repository

import com.unifydream.movy.data.api.MovieApiService
import com.unifydream.movy.data.db.FavoriteMovieDao
import com.unifydream.movy.data.db.FavoriteMovieEntity
import com.unifydream.movy.data.model.Movie
import com.unifydream.movy.data.model.MovieDetails
import com.unifydream.movy.utils.Constants.API_KEY
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: MovieApiService,
    private val movieDao: FavoriteMovieDao
) : MovieRepository {

    override suspend fun searchMovies(query: String): Result<List<Movie>> {
        if (query.isBlank()) {
            return Result.failure(Exception("Search query cannot be empty."))
        }
        return try {
            val response = apiService.searchMovies(query, API_KEY)
            if (response.isSuccessful) {
                val movies = response.body()?.results.orEmpty()
                Result.success(movies)
            } else {
                Result.failure(Exception("API Error: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMovieDetails(id: Int): Result<MovieDetails> {
        return try {
            val response = apiService.getMovieDetails(id, API_KEY)
            if (response.isSuccessful) {
                response.body()?.let { Result.success(it) }
                    ?: Result.failure(Exception("Movie details not found."))
            } else {
                Result.failure(Exception("API Error: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getFavoriteMovies(): List<FavoriteMovieEntity> {
        return movieDao.getFavoriteMovies()
    }

    override suspend fun addFavorite(movie: FavoriteMovieEntity) {
        movieDao.addMovieToFavorites(movie)
    }

    override suspend fun removeFavorite(movie: FavoriteMovieEntity) {
        movieDao.removeMovieFromFavorites(movie)
    }
}