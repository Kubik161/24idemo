package com.archonalabs.a24idemo.domain.usecase.feature.movie

import com.archonalabs.a24idemo.domain.feature.movie.model.Movie
import com.archonalabs.a24idemo.domain.usecase.UseCaseResult
import com.archonalabs.a24idemo.domain.Result
import com.archonalabs.a24idemo.domain.feature.movie.MovieRepository
import com.archonalabs.a24idemo.domain.feature.movie.model.MovieChanges
import java.util.*

/**
 * Created by Jakub Juroska on 10/14/20.
 */
class LoadMovieListUseCase(private val movieRepository: MovieRepository) : UseCaseResult<List<MovieChanges>, LoadMovieListUseCase.Params>() {

    override suspend fun doWork(params: Params): Result<List<MovieChanges>> = movieRepository.loadMovieChanges(params.startDate)

    data class Params(val startDate : String?)

}