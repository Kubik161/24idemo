package com.archonalabs.a24idemo.data.feature.movie.network

/**
 * Created by Jakub Juroska on 10/14/20.
 */
class MovieDetailDto(val title : String, val poster_path : String?, val genres : List<GenreDto>,
                     val release_date : String, val overview : String?, val original_language : String) {
}