package com.archonalabs.a24idemo.domain.feature.movie

import com.archonalabs.a24idemo.domain.feature.movie.model.Movie
import com.archonalabs.a24idemo.domain.Result
import com.archonalabs.a24idemo.domain.feature.movie.model.MovieChanges

/**
 * Created by Jakub Juroska on 10/14/20.
 */
interface MovieRepository {

    suspend fun loadMovieChanges(startDate : String?) : Result<List<MovieChanges>>

    suspend fun loadMovieDetail(id : Int) : Result<Movie>
}