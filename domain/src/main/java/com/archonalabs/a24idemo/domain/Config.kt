package com.archonalabs.a24idemo.domain

import com.archonalabs.a24idemo.domain.network.LogLevel

/**
 * Created by Jakub Juroska on 10/14/20.
 */
object Config {
    val MOVIE_DB_API_KEY_V3 = "e959ad1950fc09f5e4eefc1fb80723de"
    val MOVIE_DB_API_KEY_V4 = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlOTU5YWQxOTUwZmMwOWY1ZTRlZWZjMWZiODA3MjNkZSIsInN1YiI6IjVmODZkNWU1YTgwNjczMDAzODNhZDRkOSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.gB7NSUmGaKvNiLSh68BFRgRsryBrt3UnKP2xKnKFZ3A"
    val IMAGE_SIZE = "w500"
    val MOVIE_DETAIL_IMAGE_SIZE = "original"

    val LOG_LEVEL = LogLevel.FULL
    val API_TIMEOUT = 30L
    val REQUEST_REPEAT_DELAY = 5000L

}