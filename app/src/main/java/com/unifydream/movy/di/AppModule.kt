package com.unifydream.movy.di

import android.content.Context
import androidx.room.Room
import com.unifydream.movy.data.api.MovieApiService
import com.unifydream.movy.data.db.FavoriteMovieDao
import com.unifydream.movy.data.db.MovieDatabase
import com.unifydream.movy.data.repository.MovieRepository
import com.unifydream.movy.data.repository.MovieRepositoryImpl
import com.unifydream.movy.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideMovieApiService(retrofit: Retrofit): MovieApiService =
        retrofit.create(MovieApiService::class.java)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MovieDatabase =
        Room.databaseBuilder(context, MovieDatabase::class.java, "movies_db").build()

    @Provides
    fun provideFavoriteMovieDao(database: MovieDatabase): FavoriteMovieDao = database.favoriteMovieDao()

    @Provides
    fun provideMovieRepository(
        apiService: MovieApiService,
        movieDao: FavoriteMovieDao
    ): MovieRepository = MovieRepositoryImpl(apiService, movieDao)
}