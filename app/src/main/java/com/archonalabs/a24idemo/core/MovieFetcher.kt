package com.archonalabs.a24idemo.core

/**
 * Created by Jakub Juroska on 10/14/20.
 */
interface MovieFetcher {
    fun loadMovieDetail(movieId : Int, moviePosition: Int)

}