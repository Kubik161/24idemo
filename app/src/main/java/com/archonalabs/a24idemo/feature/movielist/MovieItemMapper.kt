package com.archonalabs.a24idemo.feature.movielist

import com.archonalabs.a24idemo.domain.feature.movie.model.Movie
import com.archonalabs.a24idemo.domain.feature.movie.model.MovieChanges
import com.archonalabs.a24idemo.domain.feature.movie.model.MovieItem

/**
 * Created by Jakub Juroska on 10/14/20.
 */
object MovieItemMapper {

    fun toMovieItem(movies: List<MovieChanges>) : List<MovieItem> {
        return movies.map {
            it.toMovieItem()
        }
    }
}

fun MovieChanges.toMovieItem() : MovieItem {
    return MovieItem(movieId = this.id, isLoaded = false, movie = Movie.empty())
}