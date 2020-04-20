package com.manwal.data.main

import androidx.lifecycle.MutableLiveData
import com.manwal.data.base.BaseViewModel
import com.manwal.model.Movie
import com.manwal.model.MovieResponse
import com.manwal.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.*

class MainViewModel(private var restClient: Retrofit) : BaseViewModel() {

    private var moviesList: MutableLiveData<List<Movie>>? = MutableLiveData()
    private var isLoading: MutableLiveData<Boolean>? = MutableLiveData()

    fun getMovies(): MutableLiveData<List<Movie>>? {
        return moviesList
    }

    fun getLoadingStatus(): MutableLiveData<Boolean>? {
        return isLoading
    }

    private fun setIsLoading(loading: Boolean) {
        isLoading!!.postValue(loading)
    }

    private fun setMovies(moviesList: List<Movie>) {
        setIsLoading(false)
        this.moviesList?.postValue(moviesList)
    }


    fun loadMoviesNetwork() {
        setIsLoading(true)

        val apiInterface = restClient.create(ApiInterface::class.java)
        val responseCall: Call<MovieResponse>? = apiInterface.getMovies()

        responseCall?.enqueue(object : Callback<MovieResponse> {

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val movieResponse = response.body();

                if (movieResponse != null) {
                    setMovies(movieResponse.moviesList!!)
                } else {
                    setMovies(Collections.emptyList())
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                setMovies(Collections.emptyList())
            }
        })
    }
}