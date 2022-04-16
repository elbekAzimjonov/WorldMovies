package com.elbek.worldmovies.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.elbek.worldmovies.R
import com.elbek.worldmovies.data.models.Result


class MovieFragment : Fragment() {
    lateinit var result: Result
    private lateinit var views: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        views = inflater.inflate(R.layout.fragment_movie, container, false)
        arguments?.let { bundle ->
            result = bundle.getParcelable("MovieData")!!
        }

        Toast.makeText(requireActivity(), result.title, Toast.LENGTH_SHORT).show()
        return views
    }

}