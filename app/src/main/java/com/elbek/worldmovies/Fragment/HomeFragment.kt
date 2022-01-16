package com.elbek.worldmovies.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elbek.worldmovies.Api.MovieApi
import com.elbek.worldmovies.Api.Result
import com.elbek.worldmovies.Api.castApi.Cast
import com.elbek.worldmovies.Models.MovieData
import com.elbek.worldmovies.R
import com.elbek.worldmovies.Retrofit.ApiClient
import com.elbek.worldmovies.Retrofit.ApiRequest
import com.elbek.worldmovies.ViewModel.MovieViewModel
import com.elbek.worldmovies.adapters.GenreAdapter
import com.elbek.worldmovies.adapters.PopularRvAdapter
import com.elbek.worldmovies.adapters.TopRvAdapter
import com.elbek.worldmovies.ui.ViewActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    lateinit var movieList: List<Result>
    lateinit var popularList: List<Result>
    lateinit var genreList: List<Result>
    lateinit var topRvAdapter: TopRvAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var popularRvView: RecyclerView
    lateinit var genre_ids: ArrayList<Int>
    lateinit var popularRvAdapter: PopularRvAdapter
    lateinit var movieViewModel: MovieViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val views = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = views.findViewById(R.id.recycler_movie)
        popularRvView = views.findViewById(R.id.popularRecycler)
        findView()
        val loading = views.findViewById<ProgressBar>(R.id.loadProgress)
        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        movieViewModel.getTopViewModel().observe(viewLifecycleOwner, {
            movieList = it.results
            topRvAdapter =
                TopRvAdapter(movieList, object : TopRvAdapter.OnItemClickListener {
                    override fun onItemClick(result: Result) {
                        genre_ids.clear()
                        for (i in 0..result.genre_ids.lastIndex) {
                            genre_ids.add(result.genre_ids[i])
                        }
                        val intent = Intent(requireActivity(), ViewActivity::class.java)
                        intent.putExtra("movies_id", result.id)
                        intent.putExtra("posterImage",result.poster_path)
                        intent.putExtra("movies_image", result.backdrop_path)
                        intent.putExtra("movies_name", result.title)
                        intent.putExtra("movies_description", result.overview)
                        intent.putExtra("movie_rating", "${result.vote_average}")
                        intent.putIntegerArrayListExtra("movie_genre_ids", genre_ids)
                        startActivity(intent)
                    }
                })
            recyclerView.layoutManager = LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            recyclerView.adapter = topRvAdapter
        })
        movieViewModel.getPopularViewModel().observe(viewLifecycleOwner, {
            popularList = it.results
            genreList = it.results
            for (i in 0..genreList.lastIndex) {
                popularRvAdapter =
                    PopularRvAdapter(
                        requireActivity(),
                        popularList,
                        object : PopularRvAdapter.OnItemClickListener {
                            override fun onItemClick(result: Result) {
                                genre_ids.clear()
                                for (i in 0..result.genre_ids.lastIndex) {
                                    genre_ids.add(result.genre_ids[i])
                                }
                                val intent =
                                    Intent(requireActivity(), ViewActivity::class.java)
                                intent.putExtra("movies_id", result.id)
                                intent.putExtra("posterImage",result.poster_path)
                                intent.putExtra("movies_image", result.backdrop_path)
                                intent.putExtra("movies_name", result.title)
                                intent.putExtra("movies_description", result.overview)
                                intent.putExtra("movie_rating", "${result.vote_average}")
                                intent.putIntegerArrayListExtra(
                                    "movie_genre_ids",
                                    genre_ids
                                )
                                startActivity(intent)
                            }
                        })
            }
            popularRvView.setHasFixedSize(true)
            popularRvView.adapter = popularRvAdapter
            loading.visibility = View.GONE
        })
        return views
    }

    fun findView() {
        movieList = ArrayList()
        popularList = ArrayList()
        genreList = ArrayList()
        genre_ids = ArrayList()
    }
}