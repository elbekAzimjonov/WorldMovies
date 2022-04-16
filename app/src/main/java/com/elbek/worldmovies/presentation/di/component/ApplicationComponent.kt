package com.elbek.worldmovies.presentation.di.component

import com.elbek.worldmovies.presentation.di.module.AppModule
import com.elbek.worldmovies.presentation.di.module.NetworkModule
import com.elbek.worldmovies.presentation.fragments.HomeFragment
import com.elbek.worldmovies.presentation.ui.MainActivity
import com.elbek.worldmovies.presentation.ui.ViewActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, AppModule::class])
interface ApplicationComponent {
    fun inject(mainActivity: MainActivity)
    fun injectHome(homeFragment: HomeFragment)
    fun injectMovie(viewActivity: ViewActivity)
}