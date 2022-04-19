package com.elbek.worldmovies.presentation.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.elbek.worldmovies.data.domain.Status
import com.elbek.worldmovies.data.models.castApi.CastActors
import com.elbek.worldmovies.data.models.CastProfile
import com.elbek.worldmovies.data.models.Movies
import com.elbek.worldmovies.presentation.viewModel.MovieViewModel
import com.elbek.worldmovies.presentation.adapters.CastAdapter
import com.elbek.worldmovies.presentation.adapters.GenreAdapter
import com.elbek.worldmovies.databinding.ActivityViewBinding
import com.elbek.worldmovies.presentation.di.App
import javax.inject.Inject

@Suppress("DEPRECATION")
class ViewActivity : AppCompatActivity() {
    private var moviesId: Int = 0

    private lateinit var castAdapter: CastAdapter
    private lateinit var castProfile: ArrayList<CastProfile>
    private lateinit var genreAdapter: GenreAdapter
    private lateinit var genreList: ArrayList<String>
    private lateinit var castData: List<CastActors>

    @Inject
    lateinit var movieViewModel: MovieViewModel

    private lateinit var videList: ArrayList<String>
    private lateinit var binding: ActivityViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        hideSystem()
        super.onCreate(savedInstanceState)
        (applicationContext.applicationContext as App).applicationComponent.injectMovie(this)
        binding = ActivityViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
        movieViewModel.getVideoLiveData(moviesId).observe(this) {
            when (it.status) {
               Status.LOADING->{

               }
                Status.SUCCESS -> {
                    try {
                        videList.add(it.data!!.results[0].key)
                    }catch (e:IndexOutOfBoundsException){

                    }
                }
                Status.ERROR->{

                }
            }
        }

        binding.floatingActionButton.setOnClickListener {
            val keyId = moviesId
            movieViewModel.getVideoLiveData(keyId).observe(this) {
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
            val movies = Movies(moviesId,
                movieImage.toString(), movieName.toString() ,movieDescription.toString(), movieRating.toString()
            )
            movieViewModel.insertMovie(movies)
            Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show()
        }
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500${movieImage}")
            .into(binding.imageView)

    }

    private fun hideSystem() {
        window?.statusBarColor = Color.TRANSPARENT
        window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }

    private fun getCost() {
        movieViewModel.getAllActor(moviesId).observe(this) {
            when (it.status) {
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    val name = it.data!!.credits.cast
                    castProfile.clear()
                    for (i in 0..it?.data.credits.cast.lastIndex) {
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
                Status.ERROR -> {

                }
            }

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