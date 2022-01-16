package com.elbek.worldmovies.ui

import android.os.Build.VERSION.SDK_INT
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.elbek.worldmovies.Api.VideoApi
import com.elbek.worldmovies.Reapository.MovieRepository
import com.elbek.worldmovies.Retrofit.ApiClient
import com.elbek.worldmovies.Retrofit.ApiRequest
import com.elbek.worldmovies.ViewModel.MovieViewModel
import com.elbek.worldmovies.databinding.ActivityPlayerBinding
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlayerActivity : YouTubeBaseActivity() {
    private lateinit var binding: ActivityPlayerBinding
    val VIDE_ID = "Bh8NeyejykU"
    val YOUTUBE_API_KEY = "AIzaSyAggN14o1RzbAHPP-Vhy1dCtw3AcnYhRo0"
    private lateinit var youTubePlayer: YouTubePlayerView
    lateinit var youtubePayerInit: YouTubePlayer.OnInitializedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val movie_id = intent.getStringExtra("movies_key")
//           apiService.getMovieKey(movie_id).enqueue(object : Callback<VideoApi> {
//            override fun onResponse(call: Call<VideoApi>, response: Response<VideoApi>) {
//                if (response.isSuccessful) {
//                    val videApi = response.body()
//                    Log.v("VideApi","$videApi")
//                    videList.add(videApi!!.results[0].key)
//                }
//            }
//
//            override fun onFailure(call: Call<VideoApi>, t: Throwable) {
//                Log.v("VideApi","$t")
//            }
//        })
        hideSystem()
        youTubePlayer = binding.youtubePlayer
        youtubePayerInit = object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                p1: YouTubePlayer?,
                p2: Boolean
            ) {
                p1?.loadVideo(movie_id)
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                Toast.makeText(this@PlayerActivity, "Not Found Video", Toast.LENGTH_SHORT).show()
            }

        }
        youTubePlayer.initialize(YOUTUBE_API_KEY, youtubePayerInit)
    }

    private fun hideSystem() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}