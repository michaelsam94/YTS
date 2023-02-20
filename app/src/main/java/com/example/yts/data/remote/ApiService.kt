package com.example.yts.data.remote

import com.example.yts.data.model.MoviesResponse
import retrofit2.http.GET

interface ApiService {

    @GET("list_movies.json")
    suspend fun getMovies(): MoviesResponse
}