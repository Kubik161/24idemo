package com.archonalabs.a24idemo.data.feature.movie.network

import com.archonalabs.a24idemo.domain.Constants
import com.archonalabs.a24idemo.domain.feature.movie.model.Movie
import com.archonalabs.a24idemo.domain.feature.movie.model.MovieChanges

/**
 * Created by Jakub Juroska on 10/14/20.
 */
object MovieMapper {

    fun fromDto(input: MovieChangesDto) : List<MovieChanges> {
        return input.results.map { it.fromDto() }
    }

    fun fromDto(input: MovieDetailDto) : Movie {
        return input.fromDto()
    }
}

//if adult not specified, assume not adult
fun MovieChangesItemDto.fromDto(): MovieChanges {
    return MovieChanges(
        id = this.id,
        adult = this.adult ?: false)
}

fun MovieDetailDto.fromDto(): Movie {
    return Movie(
        title = this.title,
        posterPath = this.poster_path ?: Constants.NO_DATA_TEXT)
}