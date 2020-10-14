package com.archonalabs.a24idemo.feature.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.archonalabs.a24idemo.R
import com.archonalabs.a24idemo.databinding.FragmentMovieDetailBinding
import com.archonalabs.a24idemo.databinding.FragmentMovieListBinding
import com.archonalabs.a24idemo.domain.Config
import com.archonalabs.a24idemo.domain.Constants
import com.archonalabs.a24idemo.domain.feature.movie.model.Movie
import com.archonalabs.a24idemo.feature.movielist.MovieListAdapter
import com.archonalabs.a24idemo.feature.movielist.MovieListVM
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_movie_detail.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.ArrayList

/**
 * Created by Jakub Juroska on 10/14/20.
 */
const val ARG_MOVIE_ID = "movie_id"
class MovieDetailFragment : Fragment() {

    private val viewModel: MovieDetailVM by viewModel()
    private lateinit var binding: FragmentMovieDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_detail, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.setMovieId(arguments?.getInt(ARG_MOVIE_ID) ?: 0)

        viewModel.movieDetail.observe(this, { movieDetail: Movie? ->
            if (movieDetail != null) {
                //update recycler view
                binding.title.text = movieDetail.title
                binding.language.text = movieDetail.language
                binding.genre.text = movieDetail.genre
                binding.overview.text = movieDetail.overview
                binding.releaseDate.text = movieDetail.releaseDate

                //load poster image
                Picasso.get()
                    .load(Constants.IMAGE_API_URL + Config.MOVIE_DETAIL_IMAGE_SIZE + movieDetail.posterPath)
                    .placeholder(R.drawable.picasso_loading_animation)
                    .resize(binding.poster.width, 0)
                    .error(R.drawable.no_img)
                    .into(binding.poster)
            }
        })
    }

    override fun onResume() {
        super.onResume()

        viewModel.loadMovieDetail()
    }

    companion object {
        fun newInstance(movieId: Int): MovieDetailFragment {
            val fragment = MovieDetailFragment()
            val args = Bundle()

            args.putInt(ARG_MOVIE_ID, movieId)
            fragment.arguments = args
            return fragment
        }
    }
}