package com.archonalabs.a24idemo.core

/**
 * Created by Jakub Juroska on 10/14/20.
 */
interface FragmentRouter {
    fun showMovieDetail(movieId : Int, moviePosition : Int)
    fun clearMovieDetail()
    fun clearMoviePosition()
}