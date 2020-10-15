package com.archonalabs.a24idemo.feature.movielist

import android.content.Context
import android.os.Handler
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.archonalabs.a24idemo.core.FragmentRouter
import com.archonalabs.a24idemo.core.MovieFetcher
import com.archonalabs.a24idemo.domain.Config
import com.archonalabs.a24idemo.domain.Result
import com.archonalabs.a24idemo.domain.feature.movie.model.MovieItem
import com.archonalabs.a24idemo.domain.usecase.feature.movie.LoadMovieDetailUseCase
import com.archonalabs.a24idemo.domain.usecase.feature.movie.LoadMovieListUseCase
import com.archonalabs.a24idemo.feature.MovieUtils
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Created by Jakub Juroska on 10/14/20.
 */
const val MIN_DAYS = 1
const val MAX_DAYS = 14

class MovieListVM(
    val loadMovieListUseCase: LoadMovieListUseCase,
    val loadMovieDetailUseCase: LoadMovieDetailUseCase
) : MovieFetcher, ViewModel() {

    private val _movieListAdapter = MutableLiveData<MovieListAdapter>()
    val movieListAdapter : LiveData<MovieListAdapter> = _movieListAdapter

    val showLoading = ObservableBoolean()
    val days = ObservableInt(MIN_DAYS)

    private fun showLoading() {
        showLoading.set(true)
    }

    private fun hideLoading() {
        showLoading.set(false)
    }

    fun loadMovieList(context : Context, router: FragmentRouter) {
        showLoading()

        viewModelScope.launch {
            //use start date as (Today - Days)
            val startDate = MovieUtils.calculateStartDate(days.get())
            when(val result = loadMovieListUseCase(LoadMovieListUseCase.Params(startDate))) {
                is Result.Success -> {
                    hideLoading()

                    Timber.d("movie list was refreshed")

                    //map data to recycler items model
                    val data = MovieItemMapper.toMovieItem(result.data)

                    //create new adapter with proper model
                    val adapter = MovieListAdapter(context, data.toMutableList(), this@MovieListVM, router)
                    adapter.setHasStableIds(true)

                    _movieListAdapter.postValue(adapter)
                }

                is Result.Error -> {
                    hideLoading()
                    Timber.e(result.error.throwable, "ERROR: %s", result.error.message)
                    Handler().postDelayed({
                        loadMovieList(context, router)
                    }, Config.REQUEST_REPEAT_DELAY)
                }
            }
        }
    }

    override fun loadMovieDetail(movieId: Int, moviePosition: Int) {
        Timber.d("loadMovieDetail %d", movieId)
        viewModelScope.launch {
            when (val result = loadMovieDetailUseCase(LoadMovieDetailUseCase.Params(movieId = movieId))) {
                is Result.Success -> {

                    val adapter = _movieListAdapter.value

                    adapter?.updateItem(MovieItem(movieId = movieId, isLoaded = true, movie = result.data), moviePosition)
                }

                is Result.Error -> {
                    Timber.e(result.error.throwable, "ERROR: %s", result.error.message)
                    //todo: we should stop repeating requests after X unsuccessful attempts
                    Handler().postDelayed({
                        loadMovieDetail(movieId = movieId, moviePosition = moviePosition)
                    }, Config.REQUEST_REPEAT_DELAY)
                }
            }
        }
    }

    fun daysChanged(input: CharSequence) {
        val parsedInput = input.toString().toIntOrNull()
        if (parsedInput != null) {
            days.set(
                when {
                    parsedInput < MIN_DAYS -> MIN_DAYS
                    parsedInput > MAX_DAYS -> MAX_DAYS
                    else -> parsedInput
                }
            )
        } else {
            days.set(MIN_DAYS)
        }
    }
}