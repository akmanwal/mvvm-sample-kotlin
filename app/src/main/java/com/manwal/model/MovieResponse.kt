package com.manwal.model

import com.google.gson.annotations.SerializedName
import com.manwal.model.Movie

data class MovieResponse(@SerializedName("movies") val moviesList: List<Movie>? = null)