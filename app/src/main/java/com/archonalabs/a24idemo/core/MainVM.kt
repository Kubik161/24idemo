package com.archonalabs.a24idemo.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Jakub Juroska on 10/14/20.
 */
class MainVM(): FragmentRouter, ViewModel() {

    private val _showMovieDetail = MutableLiveData<Int>()
    val showMovieDetail : LiveData<Int> = _showMovieDetail

    override fun showMovieDetail(movieId : Int) {
        _showMovieDetail.postValue(movieId)
    }

    override fun clearMovieDetail() {
        _showMovieDetail.postValue(0)
    }
}