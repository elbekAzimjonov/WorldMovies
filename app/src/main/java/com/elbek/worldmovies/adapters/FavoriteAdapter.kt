package com.elbek.worldmovies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.elbek.worldmovies.models.Movies
import com.elbek.worldmovies.R
import com.elbek.worldmovies.databinding.HorizontalRvItemBinding

class FavoriteAdapter(var moviesList: ArrayList<Movies>) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    inner class FavoriteViewHolder(var horizontalRvItemBinding: HorizontalRvItemBinding) :
        RecyclerView.ViewHolder(horizontalRvItemBinding.root) {
        fun onBind(movies: Movies, position: Int) {
            horizontalRvItemBinding.movieName.text = movies.moviesName
            horizontalRvItemBinding.movieRating.text = movies.moviesRating
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w342${movies.moviesImage}")
                .transform(CenterCrop())
                .placeholder(R.drawable.themoviedb)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(horizontalRvItemBinding.movieImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            HorizontalRvItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.onBind(moviesList[position],position)
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }
}