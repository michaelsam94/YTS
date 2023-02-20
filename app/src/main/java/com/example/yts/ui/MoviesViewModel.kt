package com.example.yts.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yts.data.MoviesRepository
import com.example.yts.data.model.Movie
import com.example.yts.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    var movies = MutableLiveData<Resource<Movie>>()

    init {
        getHomeNews()
    }

    fun getHomeNews() {
        movies.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val result = moviesRepository.getMovies()
            movies.postValue(Resource.Success(result))
            if(result.isNullOrEmpty()){
                movies.postValue(Resource.Error(msg="No data saved "))
            }
        }
    }

    fun getMovies() = movies as LiveData<Resource<Movie>>

}