package com.example.yts.data.remote

import com.example.yts.data.model.Movie

interface OnlineDataSource {
    suspend fun getMovies(): List<Movie> = emptyList()
}