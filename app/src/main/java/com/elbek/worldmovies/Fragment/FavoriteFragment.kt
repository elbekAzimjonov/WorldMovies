package com.elbek.worldmovies.Fragment

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.elbek.worldmovies.Models.Movies
import com.elbek.worldmovies.R
import com.elbek.worldmovies.ViewModel.MovieViewModel
import com.elbek.worldmovies.adapters.FavoriteAdapter
import com.elbek.worldmovies.adapters.MoviesItemDeclaration
import com.elbek.worldmovies.databinding.FavoriteItemBinding
import com.elbek.worldmovies.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {
    lateinit var movieViewModel: MovieViewModel
    lateinit var fragmentFavoriteBinding: FragmentFavoriteBinding
    lateinit var favoriteAdapter: FavoriteAdapter
    lateinit var movies: Movies
    lateinit var list: ArrayList<Movies>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentFavoriteBinding = FragmentFavoriteBinding.inflate(layoutInflater)
        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        movieViewModel.initDatabase(requireContext())
        list = ArrayList()

        movieViewModel.getDbViewModel().observe(viewLifecycleOwner, {
            if (it.size == 0) {
                fragmentFavoriteBinding.animationFaund.visibility = View.VISIBLE
            } else {
                fragmentFavoriteBinding.animationFaund.visibility = View.INVISIBLE
                for (i in 0..it.lastIndex) {
                    var name = it[i]
                    list.add(name)
                    Log.v("MovieDb", "Db:${name.moviesName}")
                }
                favoriteAdapter = FavoriteAdapter(list)
                fragmentFavoriteBinding.favoriteFragmentRecycler.addItemDecoration(
                    MoviesItemDeclaration(
                        resources.getDimension(R.dimen.dp8).toInt(),
                        resources.getDimension(R.dimen.dp18).toInt()
                    )
                )
                fragmentFavoriteBinding.favoriteFragmentRecycler.layoutManager =
                    GridLayoutManager(requireActivity(), 2)

                fragmentFavoriteBinding.favoriteFragmentRecycler.adapter = favoriteAdapter
            }
        })


        return fragmentFavoriteBinding.root
    }

}