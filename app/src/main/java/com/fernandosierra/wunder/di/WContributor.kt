package com.fernandosierra.wunder.di

import com.fernandosierra.wunder.presentation.map.MapActivity
import com.fernandosierra.wunder.presentation.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class WContributor {

    @ContributesAndroidInjector
    abstract fun splashActivity(): SplashActivity

    @ContributesAndroidInjector
    abstract fun mapActivity(): MapActivity
}