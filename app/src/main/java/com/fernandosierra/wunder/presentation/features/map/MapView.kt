package com.fernandosierra.wunder.presentation.features.map

import com.fernandosierra.wunder.R
import com.fernandosierra.wunder.domain.model.Placemark
import com.fernandosierra.wunder.presentation.ActivityView
import com.fernandosierra.wunder.presentation.ViewState
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import timber.log.Timber
import javax.inject.Inject
import kotlin.properties.Delegates

class MapView @Inject constructor(activity: MapActivity) : ActivityView<MapActivity>(activity) {
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var googleMap: GoogleMap
    private lateinit var markers: MutableList<Marker>
    override var state by Delegates.observable<ViewState>(ViewState.Created()) { _, _, new ->
        when (new) {
            is MapViewState.Init -> onInit()
            is MapViewState.Ready -> onReady()
            is ViewState.Success<*> -> onSuccess()
            is MapViewState.Focused -> onFocused()
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
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car))
                    .title(placemark.name))
        }.toMutableList()
    }

    override fun onError() {
        Timber.e((state as ViewState.Error).error)
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun onReady() {
        val readyState = state as MapViewState.Ready
        googleMap = readyState.googleMap
        googleMap.uiSettings.isMapToolbarEnabled = false
        googleMap.setOnMapClickListener(readyState.mapClickCallback)
        googleMap.setOnMarkerClickListener(readyState.markerClickCallback)
    }

    private fun onFocused() {
        markers.map {
            if (it.title != (state as MapViewState.Focused).focusedMarker.title) {
                it.isVisible = false
            }
        }
    }

    sealed class MapViewState : ViewState() {
        class Init(val callback: OnMapReadyCallback) : MapViewState()

        class Ready(val googleMap: GoogleMap,
                    val markerClickCallback: (Marker) -> Boolean,
                    val mapClickCallback: (LatLng) -> Unit) : MapViewState()

        class Focused(val focusedMarker: Marker) : MapViewState()
    }
}