package com.example.yts.data.remote

import kotlinx.coroutines.flow.flow

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override fun getMovies()= flow { emit(apiService.getMovies()) }
}