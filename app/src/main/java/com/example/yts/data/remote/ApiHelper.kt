package com.example.yts.data.remote

import com.example.yts.data.model.MoviesResponse
import kotlinx.coroutines.flow.Flow

interface ApiHelper {

    fun getMovies(): Flow<MoviesResponse>
}