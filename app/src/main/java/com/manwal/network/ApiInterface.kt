package com.manwal.network

import com.manwal.model.MovieResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("movies/")
    fun getMovies(): Call<MovieResponse>?
}