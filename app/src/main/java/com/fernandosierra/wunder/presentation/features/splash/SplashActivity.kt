package com.fernandosierra.wunder.presentation.features.splash

import android.os.Bundle
import com.fernandosierra.wunder.R
import com.fernandosierra.wunder.presentation.custom.WActivity

class SplashActivity : WActivity<SplashPresenter>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        presenter.init()
    }

    override fun onResume() {
        super.onResume()
        presenter.updateLocations()
    }
}
