package com.example.yts.utils

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.yts.data.model.Torrent
import com.google.gson.Gson

@ProvidedTypeConverter
object ArrayListConverter {

    @TypeConverter
    fun fromStringArrayList(value: ArrayList<String>): String {

        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringArrayList(value: String): ArrayList<String> {
        return try {
            Gson().fromJson<ArrayList<String>>(value)
        } catch (e: Exception) {
            arrayListOf()
        }
    }

    @TypeConverter
    fun fromTorrentArrayList(value: ArrayList<Torrent>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toTorrentArrayList(value: String): ArrayList<Torrent> {
        return try {
            Gson().fromJson<ArrayList<Torrent>>(value)
        } catch (e: Exception) {
            arrayListOf()
        }
    }
}
