package com.elbek.worldmovies.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.elbek.worldmovies.data.api.Result
import com.elbek.worldmovies.R
import com.elbek.worldmovies.databinding.VerticalRvItemBinding

class PopularRvAdapter(
    var context: Context,
    private val mResult: List<Result>,
    var onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<PopularRvAdapter.PopularViewHolder>() {
    lateinit var mContext: Context

    inner class PopularViewHolder(private val verticalRvItemBinding: VerticalRvItemBinding) :
        RecyclerView.ViewHolder(verticalRvItemBinding.root) {
        fun onBind(result: Result, position: Int) {
            val list: ArrayList<String> = ArrayList()
            mContext = context
            for (i in 0..result.genre_ids.lastIndex) {
                val name = getGenre(result.genre_ids[i])
                list.add(name)
                verticalRvItemBinding.genrePopular.layoutManager = LinearLayoutManager(
                    mContext,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                val genreAdapter: GenreAdapter = GenreAdapter(list)
                verticalRvItemBinding.genrePopular.adapter = genreAdapter
            }
            verticalRvItemBinding.root.setOnClickListener {
                onItemClickListener.onItemClick(result)
            }
            val pImage = verticalRvItemBinding.popularMovieImage
            verticalRvItemBinding.popularMovieName.text = result.title
            verticalRvItemBinding.movieRating.text = "${result.vote_average}"
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w342${result.poster_path}")
                .transform(CenterCrop())
                .placeholder(R.drawable.themoviedb)
                .into(pImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = VerticalRvItemBinding.inflate(inflater, parent, false)
        return PopularViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        holder.onBind(mResult[position], position)
    }

    override fun getItemCount(): Int {
        return mResult.size
    }

    interface OnItemClickListener {
        fun onItemClick(result: Result)
    }

    fun getGenre(id: Int): String {
        val genreMap = HashMap<Int, String>()
        genreMap[28] = "Action"
        genreMap[12] = "Adventure"
        genreMap[16] = "Animation"
        genreMap[35] = "Comedy"
        genreMap[80] = "Crime"
        genreMap[99] = "Documentary"
        genreMap[18] = "Drama"
        genreMap[10751] = "Family"
        genreMap[14] = "Fantasy"
        genreMap[36] = "History"
        genreMap[27] = "Horror"
        genreMap[10402] = "Music"
        genreMap[9648] = "Mystery"
        genreMap[10749] = "Romance"
        genreMap[878] = "Science Fiction"
        genreMap[10770] = "TV Movie"
        genreMap[53] = "Thriller"
        genreMap[10752] = "War"
        genreMap[37] = "Western"

        return genreMap[id]!!
    }
}