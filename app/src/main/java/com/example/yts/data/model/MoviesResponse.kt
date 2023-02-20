package com.example.yts.data.model

import com.google.gson.annotations.SerializedName

data class MoviesResponse(

    @SerializedName("data")
    val data: Data,
    @SerializedName("status_message")
    val statusMessage: String?,
    @SerializedName("status")
    val status: String?
)

data class Data (
    val movie_count: Int,
    val limit: Int,
    val page_number: Int,
    val movies: ArrayList<Movie>
)
