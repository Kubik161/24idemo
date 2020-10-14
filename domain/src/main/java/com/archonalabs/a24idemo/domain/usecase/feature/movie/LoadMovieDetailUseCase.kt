package com.archonalabs.a24idemo.domain.usecase.feature.movie

import com.archonalabs.a24idemo.domain.Result
import com.archonalabs.a24idemo.domain.feature.movie.MovieRepository
import com.archonalabs.a24idemo.domain.feature.movie.model.Movie
import com.archonalabs.a24idemo.domain.usecase.UseCaseResult

/**
 * Created by Jakub Juroska on 10/14/20.
 */
class LoadMovieDetailUseCase(private val movieRepository: MovieRepository) : UseCaseResult<Movie, LoadMovieDetailUseCase.Params>() {

    override suspend fun doWork(params: Params): Result<Movie> = movieRepository.loadMovieDetail(params.movieId)

    data class Params(val movieId : Int)

}