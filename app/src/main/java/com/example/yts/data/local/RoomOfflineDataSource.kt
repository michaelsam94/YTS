package com.example.yts.data.local

import com.example.yts.data.model.Movie

class RoomOfflineDataSource(private val movieDao: MovieDao) : OfflineDataSource {

    override fun getMovies(): List<Movie> = movieDao.getAllMovies()

    override suspend fun cacheMovies(data: List<Movie>) {
        movieDao.insertList(data)
    }

    override suspend fun deleteAllMovies() {
        movieDao.deleteAllMovies()
    }
}