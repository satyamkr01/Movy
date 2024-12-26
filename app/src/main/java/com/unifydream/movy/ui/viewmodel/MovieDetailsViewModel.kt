package com.unifydream.movy.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifydream.movy.data.db.FavoriteMovieEntity
import com.unifydream.movy.data.model.MovieDetails
import com.unifydream.movy.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    val movieDetails = MutableLiveData<MovieDetails>()
    val errorMessage = MutableLiveData<String>()
    val isFavorite = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()

    fun fetchMovieDetails(id: Int) {
        isLoading.postValue(true)
        viewModelScope.launch {
            val result = repository.getMovieDetails(id)
            isLoading.postValue(false)
            if (result.isSuccess) {
                movieDetails.postValue(result.getOrNull()!!)
            } else {
                errorMessage.postValue(result.exceptionOrNull()?.message)
            }
        }
    }

    fun toggleFavorite(movie: FavoriteMovieEntity) {
        viewModelScope.launch {
            if (isFavorite.value == true) {
                repository.removeFavorite(movie)
                isFavorite.postValue(false)
            } else {
                repository.addFavorite(movie)
                isFavorite.postValue(true)
            }
        }
    }

    fun checkIfFavorite(movieId: Int) {
        viewModelScope.launch {
            val favorites = repository.getFavoriteMovies()
            isFavorite.postValue(favorites.any { it.id == movieId })
        }
    }
}