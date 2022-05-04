

package com.elbek.worldmovies.presentation.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.elbek.worldmovies.R
import com.elbek.worldmovies.databinding.ActivityMainBinding
import com.elbek.worldmovies.presentation.di.App
import com.elbek.worldmovies.presentation.fragments.FavoriteFragment
import com.elbek.worldmovies.presentation.fragments.HomeFragment
import com.elbek.worldmovies.presentation.fragments.SettingsFragment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.onesignal.OneSignal

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private val ONESIGNAL_APP_ID = "8d5594bb-8dae-4b4f-b2a7-48d64f5b3946"
    private lateinit var binding: ActivityMainBinding
    lateinit var mAdView : AdView
    private final var TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as App).applicationComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
        //ca-app-pub-9183950610372092/8983525229
        val adView = AdView(this)
        adView.adSize = AdSize.BANNER
        adView.adUnitId = "ca-app-pub-3940256099942544/6300978111"
        MobileAds.initialize(this) {}
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

       val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val saveBoolean = sharedPreferences.getBoolean("BOOLEAN_KEY",false)
        if (saveBoolean) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        val homeFragment = HomeFragment()
        val favoriteFragment = FavoriteFragment()
        val settingsFragment = SettingsFragment()
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.cinema_menu -> {
                    makeCurrentFragment(homeFragment)
                }
                R.id.bookmark_menu -> {
                    makeCurrentFragment(favoriteFragment)
                }
                R.id.settings_menu -> {
                    makeCurrentFragment(settingsFragment)
                }
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView, fragment)
            commit()
        }
    }
}