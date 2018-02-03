package com.fernandosierra.wunder.di

import com.fernandosierra.wunder.presentation.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class WContributor {

    @ContributesAndroidInjector
    abstract fun splashActivity(): SplashActivity
}