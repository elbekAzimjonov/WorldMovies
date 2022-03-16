package com.elbek.worldmovies.presentation.fragments

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import com.elbek.worldmovies.R

class SettingsFragment : Fragment() {
    private lateinit var valueAnimator: ValueAnimator
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        val switch = view.findViewById<Switch>(R.id.switchDark)
        val image = view.findViewById<ImageView>(R.id.nigth_image)
        val sharedPreferences =
            this.requireActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val saveBoolean = sharedPreferences.getBoolean("BOOLEAN_KEY", false)
        if (saveBoolean) {
            image.setImageResource(R.drawable.sun)
        } else {
            image.setImageResource(R.drawable.moon_dark)
        }
        switch!!.isChecked = saveBoolean
        valueAnimator = ValueAnimator()
        valueAnimator.duration = 1500
        valueAnimator.setEvaluator(ArgbEvaluator())
        valueAnimator.setIntValues(Color.WHITE, Color.BLACK)
        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                saveData(isChecked)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                saveData(false)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        loadData()
        return view
    }

    private fun loadData() {
        val sharedPreferences =
            this.requireActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val saveBoolean = sharedPreferences.getBoolean("BOOLEAN_KEY", false)
    }

    private fun saveData(check: Boolean) {
        val sharedPreferences =
            this.requireActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val edit = sharedPreferences.edit()
        edit.apply {
            putBoolean("BOOLEAN_KEY", check)
        }.apply()
    }
}