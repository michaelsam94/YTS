package com.example.yts.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.yts.data.model.Movie

@Dao
interface MovieDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(movie: List<Movie>):List<Long>


    @Query("SELECT * FROM  Movie")
    fun getAllMovies(): List<Movie>

    @Query("DELETE FROM Movie")
    suspend fun deleteAllMovies()


}