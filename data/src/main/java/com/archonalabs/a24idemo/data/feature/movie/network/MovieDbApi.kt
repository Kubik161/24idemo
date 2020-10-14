package com.archonalabs.a24idemo.data.feature.movie.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Jakub Juroska on 10/14/20.
 */
interface MovieDbApi {

    @GET("movie/changes")
    fun getMovieChanges(
        @Query("start_date") startDate : String?)
            : Call<MovieChangesDto>

    @GET("movie/{movie_id}")
    fun getMovieDetail(
        @Path("movie_id") movieId: Int)
            : Call<MovieDetailDto>

}