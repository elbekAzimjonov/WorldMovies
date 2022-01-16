package com.elbek.worldmovies.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.elbek.worldmovies.Api.VideoApi
import com.elbek.worldmovies.Api.castApi.CastActors
import com.elbek.worldmovies.Models.CastProfile
import com.elbek.worldmovies.Models.Movies
import com.elbek.worldmovies.R
import com.elbek.worldmovies.Reapository.MovieRepository
import com.elbek.worldmovies.Retrofit.ApiClient
import com.elbek.worldmovies.Retrofit.ApiRequest
import com.elbek.worldmovies.ViewModel.MovieViewModel
import com.elbek.worldmovies.adapters.CastAdapter
import com.elbek.worldmovies.adapters.GenreAdapter
import com.elbek.worldmovies.databinding.ActivityViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewActivity : AppCompatActivity() {
    var movies_id: Int = 0
    lateinit var castAdapter: CastAdapter
    lateinit var castProfile: ArrayList<CastProfile>
    lateinit var genreAdapter: GenreAdapter
    lateinit var genreList: ArrayList<String>
    lateinit var castData: List<CastActors>
    lateinit var movieViewModel: MovieViewModel
    lateinit var videList: ArrayList<String>
    private lateinit var binding: ActivityViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val apiClient = ApiClient()
        val retrofit = apiClient.getRetrofit()
        val apiService = retrofit.create(ApiRequest::class.java)
        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        movieViewModel.initDatabase(this)
        hideSystem()
        genreList = ArrayList()
        castData = ArrayList()
        castProfile = ArrayList()
        videList = ArrayList()
        movies_id = intent.getIntExtra("movies_id", 0)
        val movie_image = intent.getStringExtra("movies_image")
        val movie_name = intent.getStringExtra("movies_name")
        val posterImage = intent.getStringExtra("posterImage")
        val movie_description = intent.getStringExtra("movies_description")
        val movie_rating = intent.getStringExtra("movie_rating")
        val movie_genre_ids = intent.getIntegerArrayListExtra("movie_genre_ids")
        videList.clear()
        CoroutineScope(Dispatchers.IO).launch {
            apiService.getMovieKey(movies_id).enqueue(object : Callback<VideoApi> {
                override fun onResponse(call: Call<VideoApi>, response: Response<VideoApi>) {
                    if (response.isSuccessful) {
                        val videApi = response.body()
                        Log.v("VideoApi", "${videApi!!.results[0]}")
                        videList.add(videApi.results[0].key)
                    }
                }

                override fun onFailure(call: Call<VideoApi>, t: Throwable) {

                }
            })
        }
        binding.floatingActionButton.setOnClickListener {
            var key_id = movies_id
            movieViewModel.getVideVideModel(key_id).observe(this, {
                val intentPlayer = Intent(this, PlayerActivity::class.java)
                intentPlayer.putExtra("movies_key", "${videList[0]}")
                startActivity(intentPlayer)
            })

        }
        getCost()
        binding.viewMovieName.text = movie_name
        binding.description.text = movie_description
        binding.viewMovieRating.text = movie_rating
        binding.viewGenreRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        for (i in 0..movie_genre_ids!!.lastIndex) {
            val nameGenre = getGenre(movie_genre_ids[i])
            genreList.add(nameGenre)
        }

        genreAdapter = GenreAdapter(genreList)
        binding.viewGenreRecycler.adapter = genreAdapter
        binding.saveData.setOnClickListener {
            movieViewModel.insertMovies(
                Movies(
                    movies_id,
                    posterImage.toString(),
                    movie_name.toString(),
                    movie_description.toString(),
                    movie_rating.toString()
                )
            )
            binding.saveData.setImageResource(R.drawable.ic_baseline_bookmark_24)
        }
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${movie_image}")
            .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
            .into(binding.imageView)

    }

    private fun hideSystem() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    fun getCost() {
        movieViewModel.getActorsViewModel(movies_id).observe(this, {
            val name = it.credits.cast
            castProfile.clear()
            for (i in 0..it?.credits?.cast!!.lastIndex) {
                castProfile.add(CastProfile(name[i].profile_path, name[i].name))

            }
            binding.recyclerCast.layoutManager = LinearLayoutManager(
                this@ViewActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            castAdapter = CastAdapter(castProfile)
            binding.recyclerCast.adapter = castAdapter
        })
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

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}