package com.fernandosierra.wunder.presentation.splash

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.fernandosierra.wunder.R
import dagger.android.AndroidInjection
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {
    @Inject
    lateinit var presenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        presenter.updateLocations()
    }
}
