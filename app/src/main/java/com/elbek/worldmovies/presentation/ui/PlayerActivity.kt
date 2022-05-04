package com.elbek.worldmovies.presentation.ui

import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.elbek.worldmovies.databinding.ActivityPlayerBinding
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

class PlayerActivity : YouTubeBaseActivity() {
    private lateinit var binding: ActivityPlayerBinding

    private val youtubeApiKey = "AIzaSyAggN14o1RzbAHPP-Vhy1dCtw3AcnYhRo0"
    private lateinit var youTubePlayer: YouTubePlayerView
    private lateinit var youtubePayerInit: YouTubePlayer.OnInitializedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        hideSystem()
        setContentView(binding.root)

        binding.back.setOnClickListener {
            finish()
        }

        val movieId = intent.getStringExtra("movies_key")
        youTubePlayer = binding.youtubePlayer
        youtubePayerInit = object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                p1: YouTubePlayer?,
                p2: Boolean
            ) {
                p1?.loadVideo(movieId)
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                Toast.makeText(this@PlayerActivity, "Not Found Video", Toast.LENGTH_SHORT).show()
            }

        }
        youTubePlayer.initialize(youtubeApiKey, youtubePayerInit)
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