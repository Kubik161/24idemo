package com.archonalabs.a24idemo

import android.app.Application
import com.archonalabs.a24idemo.data.di.a24iModule
import com.archonalabs.a24idemo.data.di.networkModule
import com.archonalabs.a24idemo.di.viewModelModule
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

/**
 * Created by Jakub Juroska on 10/14/20.
 */
open class A24iDemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        initPicasso()

        initKoin(this)
    }

    private fun initKoin(appContext : Application) {

        startKoin{
            androidContext(appContext)
            modules(
                a24iModule + networkModule + viewModelModule
            )
        }
    }

    private fun initPicasso() {
        val client = OkHttpClient.Builder()
            .build()
        val picasso = Picasso.Builder(applicationContext)
            .downloader(OkHttp3Downloader(client))
        if (BuildConfig.DEBUG) {
            picasso.loggingEnabled(true)
            picasso.indicatorsEnabled(true)
        }
        Picasso.setSingletonInstance(picasso.build())
    }
}