package com.elbek.worldmovies.presentation.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elbek.worldmovies.data.api.Result
import com.elbek.worldmovies.R
import com.elbek.worldmovies.presentation.viewModel.MovieViewModel
import com.elbek.worldmovies.presentation.adapters.PopularRvAdapter
import com.elbek.worldmovies.presentation.adapters.TopRvAdapter
import com.elbek.worldmovies.presentation.ui.ViewActivity

@Suppress("NAME_SHADOWING")
class HomeFragment : Fragment() {
    private lateinit var views: View
    private lateinit var loading: ProgressBar
    private lateinit var movieList: List<Result>
    private lateinit var genreList: List<Result>
    private lateinit var genreId: ArrayList<Int>
    private lateinit var popularList: List<Result>
    private lateinit var recyclerView: RecyclerView
    private lateinit var topRvAdapter: TopRvAdapter
    private lateinit var popularRvView: RecyclerView
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var popularRvAdapter: PopularRvAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        views = inflater.inflate(R.layout.fragment_home, container, false)
        findView()
        movieViewModel.getTopViewModel().observe(viewLifecycleOwner) {
            movieList = it.results
            topRvAdapter =
                TopRvAdapter(movieList, object : TopRvAdapter.OnItemClickListener {
                    override fun onItemClick(result: Result) {
                        genreId.clear()
                        for (i in 0..result.genre_ids.lastIndex) {
                            genreId.add(result.genre_ids[i])
                        }
                        val intent = Intent(requireActivity(), ViewActivity::class.java)
                        intent.putExtra("movies_id", result.id)
                        intent.putExtra("posterImage", result.poster_path)
                        intent.putExtra("movies_image", result.backdrop_path)
                        intent.putExtra("movies_name", result.title)
                        intent.putExtra("movies_description", result.overview)
                        intent.putExtra("movie_rating", "${result.vote_average}")
                        intent.putIntegerArrayListExtra("movie_genre_ids", genreId)
                        startActivity(intent)
                    }
                })
            recyclerView.layoutManager = LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            recyclerView.adapter = topRvAdapter
        }
        movieViewModel.getPopularViewModel().observe(viewLifecycleOwner) {
            popularList = it.results
            genreList = it.results
            for (i in 0..genreList.lastIndex) {
                popularRvAdapter =
                    PopularRvAdapter(
                        requireActivity(),
                        popularList,
                        object : PopularRvAdapter.OnItemClickListener {
                            override fun onItemClick(result: Result) {
                                genreId.clear()
                                for (i in 0..result.genre_ids.lastIndex) {
                                    genreId.add(result.genre_ids[i])
                                }
                                val intent =
                                    Intent(requireActivity(), ViewActivity::class.java)
                                intent.putExtra("movies_id", result.id)
                                intent.putExtra("posterImage", result.poster_path)
                                intent.putExtra("movies_image", result.backdrop_path)
                                intent.putExtra("movies_name", result.title)
                                intent.putExtra("movies_description", result.overview)
                                intent.putExtra("movie_rating", "${result.vote_average}")
                                intent.putIntegerArrayListExtra(
                                    "movie_genre_ids",
                                    genreId
                                )
                                startActivity(intent)
                            }
                        })
            }
            popularRvView.setHasFixedSize(true)
            popularRvView.adapter = popularRvAdapter
            loading.visibility = View.GONE
        }
        return views
    }

    private fun findView() {
        recyclerView = views.findViewById(R.id.recycler_movie)
        popularRvView = views.findViewById(R.id.popularRecycler)
        loading = views.findViewById(R.id.loadProgress)
        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        movieList = ArrayList()
        popularList = ArrayList()
        genreList = ArrayList()
        genreId = ArrayList()
    }
}