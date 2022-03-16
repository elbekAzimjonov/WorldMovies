package com.elbek.worldmovies.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.elbek.worldmovies.api.Result
import com.elbek.worldmovies.R
import com.elbek.worldmovies.databinding.VerticalRvItemBinding

class PopularRvAdapter(
    var context: Context,
    val pResult: List<Result>,
    var onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<PopularRvAdapter.PopularViewHolder>() {
    lateinit var mcontext: Context

    inner class PopularViewHolder(val verticalRvItemBinding: VerticalRvItemBinding) :
        RecyclerView.ViewHolder(verticalRvItemBinding.root) {
        fun onBind(result: Result, position: Int) {
            var list: ArrayList<String>
            list = ArrayList()
            mcontext = context
            for (i in 0..result.genre_ids.lastIndex) {
                val genreAdapter: GenreAdapter
                val name = getGenre(result.genre_ids[i])
                list.add(name)
                verticalRvItemBinding.genrePopular.layoutManager = LinearLayoutManager(
                    mcontext,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                genreAdapter = GenreAdapter(list)
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
        holder.onBind(pResult[position], position)
    }

    override fun getItemCount(): Int {
        return pResult.size
    }

    interface OnItemClickListener {
        fun onItemClick(result: Result)
    }

    fun getGenre(id: Int): String {
        val genreMap = HashMap<Int, String>()
        genreMap.put(28, "Action")
        genreMap.put(12, "Adventure")
        genreMap.put(16, "Animation")
        genreMap.put(35, "Comedy")
        genreMap.put(80, "Crime")
        genreMap.put(99, "Documentary")
        genreMap.put(18, "Drama")
        genreMap.put(10751, "Family")
        genreMap.put(14, "Fantasy")
        genreMap.put(36, "History")
        genreMap.put(27, "Horror")
        genreMap.put(10402, "Music")
        genreMap.put(9648, "Mystery")
        genreMap.put(10749, "Romance")
        genreMap.put(878, "Science Fiction")
        genreMap.put(10770, "TV Movie")
        genreMap.put(53, "Thriller")
        genreMap.put(10752, "War")
        genreMap.put(37, "Western")

        return genreMap.get(id)!!
    }
}