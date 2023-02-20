package com.example.yts.data.local

import com.example.yts.data.model.Movie

interface OfflineDataSource {
    fun getMovies(): List<Movie> = emptyList()

    suspend fun cacheMovies(data: List<Movie>){}

    suspend fun deleteAllMovies(){}

}