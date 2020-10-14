package com.archonalabs.a24idemo.data.di

import com.archonalabs.a24idemo.data.feature.movie.network.MovieDbApi
import com.archonalabs.a24idemo.data.network.AuthInterceptor
import com.archonalabs.a24idemo.data.network.LoggingInterceptor
import com.archonalabs.a24idemo.domain.Config
import com.archonalabs.a24idemo.domain.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Jakub Juroska on 10/14/20.
 */
const val LOGGING_INTERCEPTOR = "LOGGING_INTERCEPTOR"
const val AUTH_INTERCEPTOR = "AUTH_INTERCEPTOR"

val networkModule = module {

    single(named(LOGGING_INTERCEPTOR)) { LoggingInterceptor(Config.LOG_LEVEL) }

    single(named(AUTH_INTERCEPTOR)) { AuthInterceptor(Config.MOVIE_DB_API_KEY_V4) }

    single { provideMovieDbApi(retrofit = provideRetrofit(okHttpClient = provideOkHttpClient(
        interceptors = listOf(
            get(named(LOGGING_INTERCEPTOR)),
            get(named(AUTH_INTERCEPTOR))
        )
    )))
    }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(Constants.MOVIE_DB_API_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideOkHttpClient(interceptors : List<Interceptor>): OkHttpClient {
    val builder = OkHttpClient().newBuilder()

    interceptors.forEach {
        builder.addInterceptor(it)
    }

    builder.readTimeout(Config.API_TIMEOUT, java.util.concurrent.TimeUnit.SECONDS)
    builder.writeTimeout(Config.API_TIMEOUT, java.util.concurrent.TimeUnit.SECONDS)
    builder.connectTimeout(Config.API_TIMEOUT, java.util.concurrent.TimeUnit.SECONDS)

    return builder.build()
}

fun provideMovieDbApi(retrofit: Retrofit): MovieDbApi = retrofit.create(MovieDbApi::class.java)