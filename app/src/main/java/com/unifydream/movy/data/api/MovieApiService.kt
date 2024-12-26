package com.unifydream.movy.data.api

import com.unifydream.movy.data.model.MovieDetails
import com.unifydream.movy.data.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") title: String,
        @Query("api_key") apiKey: String
    ): Response<MovieResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") id: Int,
        @Query("api_key") apiKey: String
    ): Response<MovieDetails>
}