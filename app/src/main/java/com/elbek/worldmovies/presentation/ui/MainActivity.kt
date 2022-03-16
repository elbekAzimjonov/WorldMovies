package com.elbek.worldmovies.presentation.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.elbek.worldmovies.presentation.fragments.FavoriteFragment
import com.elbek.worldmovies.presentation.fragments.HomeFragment
import com.elbek.worldmovies.presentation.fragments.SettingsFragment
import com.elbek.worldmovies.R
import com.elbek.worldmovies.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sharedPreferences = getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val saveBoolean = sharedPreferences.getBoolean("BOOLEAN_KEY",false)
        if (saveBoolean) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            // MODE_NIGHT_FOLLOW_SYSTEM.
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