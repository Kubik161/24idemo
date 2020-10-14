package com.archonalabs.a24idemo.data.feature.movie

import com.archonalabs.a24idemo.domain.Result
import com.archonalabs.a24idemo.domain.feature.movie.MovieRemoteSource
import com.archonalabs.a24idemo.domain.feature.movie.MovieRepository
import com.archonalabs.a24idemo.domain.feature.movie.model.Movie
import com.archonalabs.a24idemo.domain.feature.movie.model.MovieChanges
import timber.log.Timber

/**
 * Created by Jakub Juroska on 10/14/20.
 */
class MovieRepositoryImpl(private val remoteSource: MovieRemoteSource): MovieRepository {

    override suspend fun loadMovieChanges(startDate: String?): Result<List<MovieChanges>> {

        return when(val movieChangesResult = remoteSource.loadMovieChanges(startDate)) {
            is Result.Success -> {

                //filter adult movies
                val filtered = movieChangesResult.data.filter { !it.adult }

                Result.Success(data = filtered)
            }

            is Result.Error -> Result.Error(error = movieChangesResult.error)
        }
    }

    override suspend fun loadMovieDetail(id: Int): Result<Movie> {

        return when (val movieDetailResult = remoteSource.loadMovieDetail(id)) {
            is Result.Success -> {
               //optionally save to DB here, if we need offline mode / cache
               movieDetailResult
            }

            is Result.Error -> {
                Timber.e("Loading detail for movie %d failed with: %s", id, movieDetailResult.error)
                movieDetailResult
            }
        }
    }
}