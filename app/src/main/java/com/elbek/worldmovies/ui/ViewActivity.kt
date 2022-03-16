package com.elbek.worldmovies.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.elbek.worldmovies.api.VideoApi
import com.elbek.worldmovies.api.castApi.CastActors
import com.elbek.worldmovies.models.CastProfile
import com.elbek.worldmovies.models.Movies
import com.elbek.worldmovies.R
import com.elbek.worldmovies.retrofit.ApiClient
import com.elbek.worldmovies.retrofit.ApiRequest
import com.elbek.worldmovies.viewModel.MovieViewModel
import com.elbek.worldmovies.adapters.CastAdapter
import com.elbek.worldmovies.adapters.GenreAdapter
import com.elbek.worldmovies.databinding.ActivityViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("DEPRECATION")
class ViewActivity : AppCompatActivity() {
    private var moviesId: Int = 0
    private lateinit var castAdapter: CastAdapter
    private lateinit var castProfile: ArrayList<CastProfile>
    private lateinit var genreAdapter: GenreAdapter
    private lateinit var genreList: ArrayList<String>
    private lateinit var castData: List<CastActors>
    private lateinit var movieViewModel: MovieViewModel
    private lateinit var videList: ArrayList<String>
    private lateinit var binding: ActivityViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewBinding.inflate(layoutInflater)
        hideSystem()
        setContentView(binding.root)
        val apiClient = ApiClient()
        val retrofit = apiClient.getRetrofit()
        val apiService = retrofit.create(ApiRequest::class.java)
        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        movieViewModel.initDatabase(this)
        genreList = ArrayList()
        castData = ArrayList()
        castProfile = ArrayList()
        videList = ArrayList()

        moviesId = intent.getIntExtra("movies_id", 0)
        val movieImage = intent.getStringExtra("movies_image")
        val movieName = intent.getStringExtra("movies_name")
        val posterImage = intent.getStringExtra("posterImage")
        val movieDescription = intent.getStringExtra("movies_description")
        val movieRating = intent.getStringExtra("movie_rating")
        val movieGenreId = intent.getIntegerArrayListExtra("movie_genre_ids")
        videList.clear()
        CoroutineScope(Dispatchers.IO).launch {
            apiService.getMovieKey(moviesId).enqueue(object : Callback<VideoApi> {
                override fun onResponse(call: Call<VideoApi>, response: Response<VideoApi>) {
                    if (response.isSuccessful) {
                        val videApi = response.body()
                        try {
                            videList.add(videApi!!.results[0].key)
                        } catch (v: IndexOutOfBoundsException) {

                        }
                    }
                }

                override fun onFailure(call: Call<VideoApi>, t: Throwable) {

                }
            })
        }

        binding.floatingActionButton.setOnClickListener {
            val keyId = moviesId
            movieViewModel.getVideVideModel(keyId).observe(this) {
                val intentPlayer = Intent(this, PlayerActivity::class.java)
                intentPlayer.putExtra("movies_key", videList[0])
                startActivity(intentPlayer)
            }

        }

        getCost()
        binding.viewMovieName.text = movieName
        binding.description.text = movieDescription
        binding.viewMovieRating.text = movieRating
        binding.viewGenreRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        for (i in 0..movieGenreId!!.lastIndex) {
            val nameGenre = getGenre(movieGenreId[i])
            genreList.add(nameGenre)
        }

        genreAdapter = GenreAdapter(genreList)
        binding.viewGenreRecycler.adapter = genreAdapter
        binding.saveData.setOnClickListener {
            movieViewModel.insertMovies(
                Movies(
                    moviesId,
                    posterImage.toString(),
                    movieName.toString(),
                    movieDescription.toString(),
                    movieRating.toString()
                )
            )
            binding.saveData.setImageResource(R.drawable.ic_baseline_bookmark_24)
        }
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${movieImage}")
            .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
            .into(binding.imageView)

    }

    private fun hideSystem() {
        window?.statusBarColor = Color.TRANSPARENT
        window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }

    private fun getCost() {
        movieViewModel.getActorsViewModel(moviesId).observe(this) {
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
        }
    }

    private fun getGenre(id: Int): String {
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

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}