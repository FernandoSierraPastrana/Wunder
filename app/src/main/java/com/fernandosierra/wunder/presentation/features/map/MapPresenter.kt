package com.fernandosierra.wunder.presentation.features.map

import com.fernandosierra.wunder.domain.interactor.LocationsInteractor
import com.fernandosierra.wunder.presentation.Presenter
import com.fernandosierra.wunder.presentation.ViewState
import com.fernandosierra.wunder.presentation.ViewState.Error
import com.fernandosierra.wunder.presentation.features.map.MapView.MapViewState
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.Marker
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import javax.inject.Inject

class MapPresenter @Inject constructor(view: MapView, private val locationsInteractor: LocationsInteractor) : Presenter<MapView>(view) {
    private var isMapReady = false
    fun init() {
        view.state = MapViewState.Init(createOnMapReadyCallback())
    }

    private fun createOnMapReadyCallback(): OnMapReadyCallback {
        return OnMapReadyCallback {
            if (it == null) {
                view.state = Error(RuntimeException("Unable to fetch Google Map"))
            } else {
                isMapReady = true
                view.state = MapViewState.Ready(it, createOnMarkerFocused(), {})
                updateLocations()
            }
        }
    }

    private fun createOnMarkerFocused(): (Marker) -> Boolean {
        return {
            view.state = MapViewState.Focused(it)
            true
        }
    }

    fun updateLocations() {
        if (isMapReady) {
            manageJob(async(UI) {
                try {
                    view.state = ViewState.Success(locationsInteractor.getAll())
                } catch (throwable: Throwable) {
                    view.state = Error(throwable)
                }
            })
        }
    }
}