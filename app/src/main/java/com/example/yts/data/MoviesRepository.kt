package com.example.yts.data

import com.example.yts.data.local.OfflineDataSource
import com.example.yts.data.model.Movie
import com.example.yts.data.remote.OnlineDataSource
import com.example.yts.utils.NetworkAwareHandler

class MoviesRepository(
    private val offlineDataSource: OfflineDataSource,
    private val onlineDataSource: OnlineDataSource,
    private val networkHandler: NetworkAwareHandler
) {



    suspend fun getMovies() : List<Movie> {

        return if (networkHandler.isOnline()) {
            val data = onlineDataSource.getMovies()
            offlineDataSource.cacheMovies(data)
            data
        } else {
            offlineDataSource.getMovies()
        }
    }

}