package com.unifydream.movy.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifydream.movy.data.model.Movie
import com.unifydream.movy.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    val searchResults = MutableLiveData<List<Movie>>()
    val errorMessage = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()

    fun searchMovies(query: String) {
        isLoading.postValue(true)
        viewModelScope.launch {
            val result = repository.searchMovies(query)
            isLoading.postValue(false)
            if (result.isSuccess) {
                val movies = result.getOrDefault(emptyList())
                if (movies.isEmpty()) {
                    errorMessage.postValue("No movies found.")
                } else {
                    searchResults.postValue(movies)
                }
            } else {
                errorMessage.postValue(result.exceptionOrNull()?.message)
            }
        }
    }
}