package com.archonalabs.a24idemo.di

import com.archonalabs.a24idemo.core.MainVM
import com.archonalabs.a24idemo.feature.movie.MovieDetailVM
import com.archonalabs.a24idemo.feature.movielist.MovieListVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Created by Jakub Juroska on 10/14/20.
 */
val viewModelModule : Module = module {

    viewModel {
        MainVM()
    }

    viewModel {
        MovieListVM(
            loadMovieListUseCase = get(),
            loadMovieDetailUseCase = get()
        )
    }

    viewModel {
        MovieDetailVM(
            loadMovieDetailUseCase = get()
        )
    }
}