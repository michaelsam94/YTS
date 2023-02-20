package com.example.yts.data.remote

import android.util.Log
import com.example.yts.data.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

class RetrofitOnlineDataSource(private val service: ApiHelper): OnlineDataSource {
    private val movies = mutableListOf<Movie>()

    companion object {
        var errorMsg: String = ""
    }

    override suspend fun getMovies(): List<Movie> {
        service.getMovies()
            .flowOn(Dispatchers.IO)
            .catch { e ->
                errorMsg = e.message.toString()
                Log.e("TAG", errorMsg)
            }
            .collect {
                movies.addAll(it.data.movies ?: emptyList())
            }

        return movies

    }

}