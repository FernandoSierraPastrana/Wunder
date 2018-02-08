package com.fernandosierra.wunder.presentation.features.map

import android.os.Bundle
import com.fernandosierra.wunder.R
import com.fernandosierra.wunder.presentation.custom.WActivity

class MapActivity : WActivity<MapPresenter>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        presenter.init()
    }

    override fun onResume() {
        super.onResume()
        presenter.updateLocations()
    }
}
