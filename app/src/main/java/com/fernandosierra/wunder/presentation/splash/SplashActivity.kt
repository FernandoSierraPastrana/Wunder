package com.fernandosierra.wunder.presentation.splash

import android.os.Bundle
import com.fernandosierra.wunder.R
import com.fernandosierra.wunder.presentation.custom.WActivity
import javax.inject.Inject

class SplashActivity : WActivity() {
    @Inject
    lateinit var presenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        presenter.init()
    }

    override fun onResume() {
        super.onResume()
        presenter.updateLocations()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }
}
