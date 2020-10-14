package com.archonalabs.a24idemo.domain.feature.movie.model

import com.sun.org.apache.xpath.internal.operations.Bool

/**
 * Created by Jakub Juroska on 10/14/20.
 */
data class MovieItem(val movieId : Int, val isLoaded : Boolean, val movie: Movie)