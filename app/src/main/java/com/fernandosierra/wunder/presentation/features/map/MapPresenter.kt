package com.fernandosierra.wunder.presentation.features.map

import com.fernandosierra.wunder.domain.interactor.LocationsInteractor
import com.fernandosierra.wunder.presentation.Presenter
import com.fernandosierra.wunder.presentation.ViewState
import com.fernandosierra.wunder.presentation.ViewState.Error
import com.fernandosierra.wunder.presentation.features.map.MapView.MapViewState
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import javax.inject.Inject

class MapPresenter @Inject constructor(view: MapView, private val locationsInteractor: LocationsInteractor)
    : Presenter<MapView>(view), OnMapReadyCallback {

    fun init() {
        view.state = MapViewState.Init(this)
    }

    fun updateLocations() {
        manageJob(async(UI) {
            try {
                view.state = ViewState.Success(locationsInteractor.getAll())
            } catch (throwable: Throwable) {
                view.state = Error(throwable)
            }
        })
    }

    override fun onMapReady(googleMap: GoogleMap?) = if (googleMap != null) {
        view.state = MapViewState.Ready(googleMap)
    } else {
        view.state = Error(RuntimeException("Unable to fetch Google Map"))
    }
}