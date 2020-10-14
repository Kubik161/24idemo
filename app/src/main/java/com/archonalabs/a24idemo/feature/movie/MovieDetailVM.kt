package com.archonalabs.a24idemo.feature.movie

import android.os.Handler
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.archonalabs.a24idemo.core.MovieFetcher
import com.archonalabs.a24idemo.domain.Config
import com.archonalabs.a24idemo.domain.Result
import com.archonalabs.a24idemo.domain.feature.movie.model.Movie
import com.archonalabs.a24idemo.domain.feature.movie.model.MovieItem
import com.archonalabs.a24idemo.domain.usecase.feature.movie.LoadMovieDetailUseCase
import com.archonalabs.a24idemo.feature.movielist.MovieListAdapter
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Created by Jakub Juroska on 10/14/20.
 */
class MovieDetailVM(
    val loadMovieDetailUseCase: LoadMovieDetailUseCase
) : ViewModel() {

    private var movieId : Int = 0
    private val _movieDetail = MutableLiveData<Movie>()
    val movieDetail : LiveData<Movie> = _movieDetail

    val showLoading = ObservableBoolean()

    fun setMovieId(movieId: Int) {
        this.movieId = movieId
    }

    private fun showLoading() {
        showLoading.set(true)
    }

    private fun hideLoading() {
        showLoading.set(false)
    }

    fun loadMovieDetail() {
        //showLoading()
        viewModelScope.launch {
            when (val result = loadMovieDetailUseCase(LoadMovieDetailUseCase.Params(movieId = movieId))) {
                is Result.Success -> {
                    hideLoading()
                    _movieDetail.postValue(result.data)
                }

                is Result.Error -> {
                    hideLoading()
                    Timber.e(result.error.throwable, "ERROR: %s", result.error.message)

                    Handler().postDelayed({
                        loadMovieDetail()
                    }, Config.REQUEST_REPEAT_DELAY)
                }
            }
        }

    }
}