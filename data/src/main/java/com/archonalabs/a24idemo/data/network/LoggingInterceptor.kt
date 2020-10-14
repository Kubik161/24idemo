package com.archonalabs.a24idemo.data.network

import com.archonalabs.a24idemo.domain.network.LogLevel
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import timber.log.Timber
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Created by Jakub Juroska on 10/14/20.
 */
class LoggingInterceptor(private val logLevel: LogLevel) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request : Request = chain.request()

        val t1 = System.nanoTime()
        if (logLevel >= LogLevel.FULL) {
            Timber.tag("NETWORK").i("Sending request %s %s on %s%n%s", request.method(), request.url(), chain.connection(), request.headers())
            Timber.tag("NETWORK").v("REQUEST BODY BEGIN\n%s\nREQUEST BODY END", bodyToString(request))
        }

        val response = chain.proceed(request)

        val responseBody = response.body()
        val responseBodyString = response.body()!!.string()

        // now we have extracted the response body but in the process
        // we have consumed the original response and can't read it again
        // so we need to build a new one to return from this method
        val newResponse = response.newBuilder().body(ResponseBody.create(responseBody!!.contentType(), responseBodyString.toByteArray())).build()

        val t2 = System.nanoTime()
        if (logLevel >= LogLevel.FULL) {
            Timber.tag("NETWORK").i("Received response for %s in %dms%n%s", response.request().url(), TimeUnit.NANOSECONDS.toMillis(t2 - t1), response.headers())
            Timber.tag("NETWORK").i("Response code %d", response.code())
            Timber.tag("NETWORK").v("RESPONSE BODY BEGIN:\n%s\nRESPONSE BODY END", responseBodyString)
        }

        return newResponse
    }

    private fun bodyToString(request: Request): String {
        return try {
            val copy = request.newBuilder().build()
            val buffer = Buffer()
            if (copy.body() != null) {
                copy.body()!!.writeTo(buffer)
            }
            buffer.readUtf8()
        } catch (e: IOException) {
            "did not work"
        }
    }
}