package com.archonalabs.a24idemo.feature.movielist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.archonalabs.a24idemo.R
import com.archonalabs.a24idemo.core.FragmentRouter
import com.archonalabs.a24idemo.core.MovieFetcher
import com.archonalabs.a24idemo.domain.Config
import com.archonalabs.a24idemo.domain.Constants
import com.archonalabs.a24idemo.domain.feature.movie.model.MovieItem
import com.squareup.picasso.Picasso
import timber.log.Timber

/**
 * Created by Jakub Juroska on 10/14/20.
 */
class MovieListAdapter(private val context: Context, private val data: MutableList<MovieItem>,
                       private val movieFetcher : MovieFetcher, private val fragmentRouter: FragmentRouter): RecyclerView.Adapter<MovieListAdapter.MovieItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.movie_item, parent, false)
        return MovieItemViewHolder(view, movieFetcher)
    }

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        holder.setViews(data[position], position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemId(position: Int): Long {
        return data[position].movieId.toLong()
    }

    fun updateItem(movieItem: MovieItem, position: Int) {
        data[position] = movieItem
        notifyItemChanged(position)
    }

    inner class MovieItemViewHolder(private val itemView: View, private val movieFetcher: MovieFetcher) : RecyclerView.ViewHolder(itemView) {

        private val progressView : ProgressBar = itemView.findViewById(R.id.progress_bar)
        private val titleView: TextView = itemView.findViewById(R.id.title)
        private val posterView: ImageView = itemView.findViewById(R.id.poster)

        fun setViews(item: MovieItem, position: Int) {
            //bind data to views
            if (item.isLoaded) {
                //hide progress dialog
                progressView.visibility = View.GONE
                titleView.visibility = View.VISIBLE
                posterView.visibility = View.VISIBLE

                titleView.text = item.movie.title

                //load poster image
                Picasso.get()
                    .load(Constants.IMAGE_API_URL + Config.IMAGE_SIZE + item.movie.posterPath)
                    .placeholder(R.drawable.picasso_loading_animation)
                    .error(R.drawable.no_img)
                    .into(posterView)

                itemView.setOnClickListener {
                    fragmentRouter.showMovieDetail(item.movieId)
                }
            } else {
                //show progress dialog
                progressView.visibility = View.VISIBLE

                //hide data views
                titleView.visibility = View.GONE
                posterView.visibility = View.INVISIBLE

                //load movie detail data
                movieFetcher.loadMovieDetail(movieId = item.movieId, moviePosition = position)
            }
        }
    }
}