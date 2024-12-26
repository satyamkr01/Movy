package com.unifydream.movy.ui.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.unifydream.movy.databinding.ActivityMovieDetailsBinding
import com.unifydream.movy.ui.viewmodel.MovieDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {
    private val viewModel: MovieDetailsViewModel by viewModels()
    private lateinit var binding: ActivityMovieDetailsBinding
    private var movieId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movieId = intent.getIntExtra("MOVIE_ID", -1)
        if (movieId != -1) {
            setupObservers()
            viewModel.fetchMovieDetails(movieId)
            viewModel.checkIfFavorite(movieId)
        } else {
            Toast.makeText(this, "Invalid movie ID", Toast.LENGTH_SHORT).show()
            finish()
        }

        /*binding.favoriteButton.setOnClickListener {
            viewModel.movieDetails.value?.let { movieDetails ->
                val movieEntity = FavoriteMovieEntity(movieDetails.id, movieDetails.title)
                viewModel.toggleFavorite(movieEntity)
            }
        }*/
    }

    private fun setupObservers() {
        viewModel.movieDetails.observe(this) { movie ->
            binding.textTitle.text = movie.title
            binding.textDescription.text = movie.overview
            binding.textRating.text = "Rating: ${movie.vote_average}"
            Glide.with(this).load(movie.poster_path).into(binding.imagePoster)
        }

        viewModel.isFavorite.observe(this) { isFavorite ->
            binding.favoriteButton.text = if (isFavorite) "Unfavorite" else "Favorite"
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.errorMessage.observe(this) { error ->
            if (!error.isNullOrEmpty()) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            }
        }
    }
}