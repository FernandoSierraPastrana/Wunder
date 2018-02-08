package com.fernandosierra.wunder.presentation.features.map

import com.fernandosierra.wunder.R
import com.fernandosierra.wunder.domain.model.Placemark
import com.fernandosierra.wunder.presentation.ActivityView
import com.fernandosierra.wunder.presentation.ViewState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import javax.inject.Inject
import kotlin.properties.Delegates

class MapView @Inject constructor(activity: MapActivity) : ActivityView<MapActivity>(activity) {
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var googleMap: GoogleMap
    private lateinit var markers: MutableList<Marker>
    override var state by Delegates.observable<ViewState>(ViewState.Created()) { value, old, new ->
        when (new) {
            is MapViewState.Init -> onInit()
            is MapViewState.Ready -> onReady()
            is ViewState.Success<*> ->
                if (old is MapViewState.Ready) {
                    onSuccess()
                } else {
                    throw UnsupportedOperationException("Wrong state change")
                }
            is ViewState.Error -> onError()
        }
    }

    override fun onInit() {
        view {
            mapFragment = it.supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync((state as MapViewState.Init).callback)
        }
    }

    override fun onLoading() {
        throw UnsupportedOperationException("Unsupported State")
    }

    override fun onSuccess() {
        val successState = (state as ViewState.Success<*>).item as List<*>
        markers = successState.map {
            val placemark = it as Placemark
            googleMap.addMarker(MarkerOptions().position(LatLng(placemark.latitude, placemark.longitude))
                    .title(placemark.name))
        }.toMutableList()
    }

    override fun onError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun onReady() {
        googleMap = (state as MapViewState.Ready).googleMap
        googleMap.uiSettings.isMapToolbarEnabled = false
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    sealed class MapViewState : ViewState() {
        class Init(val callback: OnMapReadyCallback) : MapViewState()
        class Ready(val googleMap: GoogleMap) : MapViewState()
    }
}