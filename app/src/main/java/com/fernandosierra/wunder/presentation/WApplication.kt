package com.fernandosierra.wunder.presentation

import android.app.Activity
import android.app.Application
import com.fernandosierra.wunder.BuildConfig
import com.fernandosierra.wunder.di.DaggerWComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.realm.Realm
import timber.log.Timber
import javax.inject.Inject

class WApplication : Application(), HasActivityInjector {
    @Inject lateinit var activityDispatcher: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Realm.init(this)

        DaggerWComponent.builder()
                .application(this)
                .build()
                .inject(this)
    }

    override fun activityInjector() = activityDispatcher
}