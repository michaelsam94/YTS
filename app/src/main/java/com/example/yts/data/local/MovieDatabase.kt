package com.example.yts.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.yts.data.model.Movie
import com.example.yts.utils.ArrayListConverter

@Database(entities = [Movie::class], version = 1 , exportSchema = false)
@TypeConverters(ArrayListConverter::class)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun getNewsDao(): MovieDao
}