package com.archonalabs.a24idemo.feature.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.archonalabs.a24idemo.R
import com.archonalabs.a24idemo.core.MainVM
import com.archonalabs.a24idemo.databinding.FragmentMovieListBinding
import com.archonalabs.a24idemo.feature.movie.MovieDetailFragment
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

/**
 * Created by Jakub Juroska on 10/14/20.
 */
class MovieListFragment : Fragment() {

    private val viewModel: MovieListVM by viewModel()
    private val mainViewModel: MainVM by viewModel()
    private lateinit var binding: FragmentMovieListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_list, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.reload.setOnClickListener {
            viewModel.loadMovieList(requireContext(), mainViewModel)
            mainViewModel.clearMoviePosition()
        }

        viewModel.movieListAdapter.observe(this, { adapter: MovieListAdapter? ->
            if (adapter != null) {
                //update recycler view
                binding.movieList.adapter = adapter

                val tablet = resources.getBoolean(R.bool.isTablet)
                if (tablet) {
                    //horizontal for Tablet
                    binding.movieList.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                } else {
                    //vertical for Phone
                    binding.movieList.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                }

                val lastPosition = mainViewModel.selectedMoviePosition.value
                if (lastPosition != null && lastPosition != 0) {
                    binding.movieList.scrollToPosition(lastPosition)
                }
            }
        })

        mainViewModel.showMovieDetail.observe(viewLifecycleOwner, Observer { movieId ->
            if (movieId != null && movieId != 0) {
                val fragmentManager = fragmentManager
                fragmentManager
                    ?.beginTransaction()
                    ?.replace(R.id.container, MovieDetailFragment.newInstance(movieId))
                    ?.addToBackStack(null)
                    ?.commitAllowingStateLoss()

                mainViewModel.clearMovieDetail()
            }
        })
    }

    override fun onResume() {
        super.onResume()

        if (binding.movieList.adapter == null) {
            viewModel.loadMovieList(requireContext(), mainViewModel)
        }
    }

    companion object {
        fun newInstance(): MovieListFragment {
            return MovieListFragment()
        }
    }
}