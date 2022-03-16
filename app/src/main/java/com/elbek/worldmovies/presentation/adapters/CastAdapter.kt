package com.elbek.worldmovies.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.elbek.worldmovies.data.models.CastProfile
import com.elbek.worldmovies.R
import com.elbek.worldmovies.databinding.ItemCastBinding

class CastAdapter(private val castList: ArrayList<CastProfile>) :
    RecyclerView.Adapter<CastAdapter.CastViewHolder>() {
    inner class CastViewHolder(private val itemCastBinding: ItemCastBinding) :
        RecyclerView.ViewHolder(itemCastBinding.root) {
        fun onBind(profile: CastProfile, position: Int) {
            itemCastBinding.castName.text = profile.castName
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w342${profile.castUrl}")
                .transform(CenterCrop())
                .placeholder(R.drawable.themoviedb)
                .into(itemCastBinding.castImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCastBinding.inflate(inflater, parent, false)
        return CastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.onBind(castList[position], position)
    }

    override fun getItemCount(): Int {
        return castList.size
    }
}