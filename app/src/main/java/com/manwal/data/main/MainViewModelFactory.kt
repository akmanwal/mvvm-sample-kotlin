package com.manwal.data.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import retrofit2.Retrofit

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val instance: Retrofit): ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(instance) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}