package com.archonalabs.a24idemo.data.di

import com.archonalabs.a24idemo.data.feature.movie.MovieRemoteSourceImpl
import com.archonalabs.a24idemo.data.feature.movie.MovieRepositoryImpl
import com.archonalabs.a24idemo.domain.feature.movie.MovieRemoteSource
import com.archonalabs.a24idemo.domain.feature.movie.MovieRepository
import com.archonalabs.a24idemo.domain.usecase.feature.movie.LoadMovieDetailUseCase
import com.archonalabs.a24idemo.domain.usecase.feature.movie.LoadMovieListUseCase
import org.koin.dsl.module

/**
 * Created by Jakub Juroska on 10/14/20.
 */
val a24iModule = module {

    //------------------------ UCs -----------------------
    single { LoadMovieListUseCase(movieRepository = get()) }

    single { LoadMovieDetailUseCase(movieRepository = get()) }

    //----------------------- REPOSITORY ------------------------------
    single<MovieRepository> { MovieRepositoryImpl(remoteSource = get()) }

    //------------------------ SOURCES -------------------------------
    single<MovieRemoteSource> { MovieRemoteSourceImpl(movieDbApi = get()) }
}