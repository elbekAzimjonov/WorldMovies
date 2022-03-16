package com.elbek.worldmovies.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elbek.worldmovies.databinding.GenreItemBinding
class GenreAdapter(val list: ArrayList<String>) :
    RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {
    inner class GenreViewHolder(val binding: GenreItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun getFind(position: Int) {
            for (i in 0..list.lastIndex) {
                val genreName: String = list[position]
                binding.genrePopular.text = genreName
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = GenreItemBinding.inflate(inflater, parent, false)
        return GenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.getFind(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}