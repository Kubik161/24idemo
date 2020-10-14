package com.archonalabs.a24idemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.archonalabs.a24idemo.core.MainVM
import com.archonalabs.a24idemo.databinding.ActivityMainBinding
import com.archonalabs.a24idemo.feature.movie.MovieDetailFragment
import com.archonalabs.a24idemo.feature.movielist.MovieListFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel : MainVM by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = mainViewModel
        binding.lifecycleOwner = this

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