package com.elbek.worldmovies.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.elbek.worldmovies.data.models.Movies
import com.elbek.worldmovies.R
import com.elbek.worldmovies.presentation.viewModel.MovieViewModel
import com.elbek.worldmovies.presentation.adapters.FavoriteAdapter
import com.elbek.worldmovies.presentation.adapters.MoviesItemDeclaration
import com.elbek.worldmovies.databinding.FragmentFavoriteBinding
import com.elbek.worldmovies.presentation.di.App
import javax.inject.Inject

class FavoriteFragment : Fragment() {
    @Inject
    lateinit var movieViewModel: MovieViewModel
    private lateinit var fragmentFavoriteBinding: FragmentFavoriteBinding
    private lateinit var favoriteAdapter: FavoriteAdapter
    lateinit var movies: Movies
    lateinit var list: ArrayList<Movies>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as App).applicationComponent.injectFavorite(this)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentFavoriteBinding = FragmentFavoriteBinding.inflate(layoutInflater)
        list = ArrayList()

        movieViewModel.getAllMoviesDb().observe(viewLifecycleOwner) {
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