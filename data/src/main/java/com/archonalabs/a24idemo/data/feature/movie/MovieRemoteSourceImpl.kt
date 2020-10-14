package com.archonalabs.a24idemo.data.feature.movie

import com.archonalabs.a24idemo.data.feature.movie.network.MovieDbApi
import com.archonalabs.a24idemo.data.feature.movie.network.MovieMapper
import com.archonalabs.a24idemo.domain.ErrorResult
import com.archonalabs.a24idemo.domain.Result
import com.archonalabs.a24idemo.domain.feature.movie.MovieRemoteSource
import com.archonalabs.a24idemo.domain.feature.movie.model.Movie
import com.archonalabs.a24idemo.domain.feature.movie.model.MovieChanges
import com.archonalabs.a24idemo.domain.safeCall
import retrofit2.awaitResponse

/**
 * Created by Jakub Juroska on 10/14/20.
 */
class MovieRemoteSourceImpl(val movieDbApi: MovieDbApi): MovieRemoteSource {

    override suspend fun loadMovieChanges(startDate : String?): Result<List<MovieChanges>> = safeCall(
        call = { fetchMovieChanges(startDate) },
        errorMessage = "Cannot load movie changes!"
    )

    private suspend fun fetchMovieChanges(startDate : String?) : Result<List<MovieChanges>> {
        val result = movieDbApi.getMovieChanges(startDate).awaitResponse()
        return if (result.isSuccessful) {
            val body = result.body()

            body?.let { data ->
                val mapped = MovieMapper.fromDto(data)
                Result.Success(data = mapped)
            } ?: Result.Error(ErrorResult("fetchMovieChanges: No response data"))

        } else {
            Result.Error(ErrorResult("fetchMovieChanges: Failed"))
        }
    }

    override suspend fun loadMovieDetail(id : Int): Result<Movie> = safeCall(
        call = { fetchMovieDetail(id) },
        errorMessage = "Cannot load movie detail!"
    )

    private suspend fun fetchMovieDetail(id: Int): Result<Movie> {
        val result = movieDbApi.getMovieDetail(id).awaitResponse()
        return if (result.isSuccessful) {
            val body = result.body()

            body?.let { data ->
                val mapped = MovieMapper.fromDto(data)
                Result.Success(data = mapped)
            } ?: Result.Error(ErrorResult("fetchMovieDetail: No response data"))

        } else {
            Result.Error(ErrorResult("fetchMovieDetail: Failed"))
        }
    }
}