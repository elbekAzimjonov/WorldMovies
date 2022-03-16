package com.elbek.worldmovies.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.elbek.worldmovies.data.api.Result
import com.elbek.worldmovies.R
import com.elbek.worldmovies.databinding.HorizontalRvItemBinding

class TopRvAdapter(
    private val topResult: List<Result>,
    var onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<TopRvAdapter.TopViewHolder>() {
    inner class TopViewHolder(private val horizontalRvItemBinding: HorizontalRvItemBinding) :
        RecyclerView.ViewHolder(horizontalRvItemBinding.root) {
        private val moviesImage = horizontalRvItemBinding.movieImage
        fun onBind(result: Result, position: Int) {
            horizontalRvItemBinding.movieName.text = result.title
            horizontalRvItemBinding.movieRating.text = "${result.vote_average}"
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w342${result.poster_path}")
                .transform(CenterCrop())
                .placeholder(R.drawable.themoviedb)
                .into(moviesImage)
            horizontalRvItemBinding.root.setOnClickListener {
                onItemClickListener.onItemClick(result)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HorizontalRvItemBinding.inflate(inflater, parent, false)
        return TopViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopViewHolder, position: Int) {
        holder.onBind(topResult[position], position)
    }

    override fun getItemCount(): Int {
        return topResult.size
    }

    interface OnItemClickListener {
        fun onItemClick(result: Result)
    }
}