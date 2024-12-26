package com.unifydream.movy.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteMovieDao {
    @Query("SELECT * FROM favorite_movies")
    suspend fun getFavoriteMovies(): List<FavoriteMovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovieToFavorites(movie: FavoriteMovieEntity)

    @Delete
    suspend fun removeMovieFromFavorites(movie: FavoriteMovieEntity)
}