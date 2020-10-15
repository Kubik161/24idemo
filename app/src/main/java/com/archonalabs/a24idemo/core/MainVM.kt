package com.archonalabs.a24idemo.core

import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Jakub Juroska on 10/14/20.
 */
class MainVM(): FragmentRouter, ViewModel() {

    private val _showMovieDetail = MutableLiveData<Int>()
    val showMovieDetail : LiveData<Int> = _showMovieDetail

    private val _selectedMoviePosition = MutableLiveData<Int>()
    val selectedMoviePosition : LiveData<Int> = _selectedMoviePosition

    override fun showMovieDetail(movieId : Int, moviePosition : Int) {
        _selectedMoviePosition.postValue(moviePosition)
        _showMovieDetail.postValue(movieId)
    }

    override fun clearMovieDetail() {
        _showMovieDetail.postValue(0)
    }

    override fun clearMoviePosition() {
        _selectedMoviePosition.postValue(0)
    }
}