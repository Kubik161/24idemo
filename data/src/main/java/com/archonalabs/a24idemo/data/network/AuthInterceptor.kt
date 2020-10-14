package com.archonalabs.a24idemo.data.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Created by Jakub Juroska on 10/14/20.
 */
class AuthInterceptor(private val apiKey: String) : Interceptor  {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer $apiKey")
            .build();

        return chain.proceed(request)
    }

}