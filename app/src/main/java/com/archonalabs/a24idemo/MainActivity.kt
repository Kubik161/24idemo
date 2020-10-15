package com.archonalabs.a24idemo

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.archonalabs.a24idemo.core.MainVM
import com.archonalabs.a24idemo.databinding.ActivityMainBinding
import com.archonalabs.a24idemo.feature.movielist.MovieListFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel : MainVM by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = mainViewModel
        binding.lifecycleOwner = this

        val tablet = resources.getBoolean(R.bool.isTablet)
        if (tablet) {
            //allow only landscape
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        } else {
            //allow only portrait
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        }

        showInitFragment()
    }

    private fun showInitFragment() {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.container, MovieListFragment.newInstance())
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }
}