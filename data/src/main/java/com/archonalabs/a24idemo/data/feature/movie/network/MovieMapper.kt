package com.archonalabs.a24idemo.data.feature.movie.network

import com.archonalabs.a24idemo.data.feature.movie.network.MovieMapper.inputDateFormat
import com.archonalabs.a24idemo.data.feature.movie.network.MovieMapper.outputDateFormat
import com.archonalabs.a24idemo.domain.Constants
import com.archonalabs.a24idemo.domain.feature.movie.model.Movie
import com.archonalabs.a24idemo.domain.feature.movie.model.MovieChanges
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Jakub Juroska on 10/14/20.
 */
object MovieMapper {

    val inputDateFormat = SimpleDateFormat("YYYY-MM-dd")
    val outputDateFormat = SimpleDateFormat("dd. MMMM YYYY")

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
        posterPath = this.poster_path ?: Constants.NO_DATA_TEXT,
        language = this.original_language,
        genre = this.genres.map { it.name }.takeIf { it.isNotEmpty() }?.reduce { acc, string -> "$acc $string" } ?: Constants.NO_DATA_TEXT,
        overview = this.overview ?: Constants.NO_DATA_TEXT,
        releaseDate = try { outputDateFormat.format(inputDateFormat.parse(this.release_date) ?: Date()) ?: this.release_date} catch (e : Exception) { this.release_date }
    )
}