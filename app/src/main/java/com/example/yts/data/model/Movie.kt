package com.example.yts.data.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Movie(
    val background_image: String,
    val background_image_original: String,
    val date_uploaded: String,
    val date_uploaded_unix: Int,
    val description_full: String,
    val genres: ArrayList<String>,
    @PrimaryKey
    @NonNull
    val id: Int,
    val imdb_code: String,
    val language: String,
    val large_cover_image: String,
    val medium_cover_image: String,
    val mpa_rating: String,
    val rating: Float,
    val runtime: Int,
    val slug: String,
    val small_cover_image: String,
    val state: String,
    val summary: String,
    val synopsis: String,
    val title: String,
    val title_english: String,
    val title_long: String,
    val torrents: ArrayList<Torrent>,
    val url: String,
    val year: Int,
    val yt_trailer_code: String
) : Serializable

data class Torrent(
    val date_uploaded: String,
    val date_uploaded_unix: Int,
    val hash: String,
    val peers: Int,
    val quality: String,
    val seeds: Int,
    val size: String,
    val size_bytes: Long,
    val type: String,
    val url: String
) : Serializable