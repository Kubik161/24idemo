package com.archonalabs.a24idemo.domain.feature.movie.model

import com.archonalabs.a24idemo.domain.Constants


/**
 * Created by Jakub Juroska on 10/14/20.
 */
data class Movie(
    val title : String,
    val posterPath : String,
    val language : String = "ENG",
    val genre : String = "Drama",
    val overview : String = "Really long text, maybe multiline?  maybe multiline  maybe multiline  maybe multiline maybe multiline  maybe multiline v  maybe multiline",
    val releaseDate : String = "27. september 2014"
) {
    companion object {
        fun empty(): Movie {
            return Movie(title = Constants.NO_DATA_TEXT, posterPath = Constants.NO_DATA_TEXT, language = Constants.NO_DATA_TEXT,
                genre = Constants.NO_DATA_TEXT, overview = Constants.NO_DATA_TEXT, releaseDate = Constants.NO_DATA_TEXT)
        }
    }
}