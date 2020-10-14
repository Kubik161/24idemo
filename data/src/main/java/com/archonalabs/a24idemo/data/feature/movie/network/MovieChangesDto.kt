package com.archonalabs.a24idemo.data.feature.movie.network

/**
 * Created by Jakub Juroska on 10/14/20.
 */
data class MovieChangesDto(val results: List<MovieChangesItemDto>, val page : Int, val total_pages: Int, val total_results: Int) {
}