package com.elbek.worldmovies.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.elbek.worldmovies.models.Movies
import com.elbek.worldmovies.R
import com.elbek.worldmovies.viewModel.MovieViewModel
import com.elbek.worldmovies.adapters.FavoriteAdapter
import com.elbek.worldmovies.adapters.MoviesItemDeclaration
import com.elbek.worldmovies.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var fragmentFavoriteBinding: FragmentFavoriteBinding
    private lateinit var favoriteAdapter: FavoriteAdapter
    lateinit var movies: Movies
    lateinit var list: ArrayList<Movies>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentFavoriteBinding = FragmentFavoriteBinding.inflate(layoutInflater)
        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        movieViewModel.initDatabase(requireContext())
        list = ArrayList()

        movieViewModel.getDbViewModel().observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                fragmentFavoriteBinding.animationFaund.visibility = View.VISIBLE
            } else {
                fragmentFavoriteBinding.animationFaund.visibility = View.INVISIBLE
                for (i in 0..it.lastIndex) {
                    val name = it[i]
                    list.add(name)
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
        }


        return fragmentFavoriteBinding.root
    }

}