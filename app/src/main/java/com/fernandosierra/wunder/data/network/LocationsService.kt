package com.fernandosierra.wunder.data.network

import com.fernandosierra.wunder.data.model.Placemarks
import io.reactivex.Single
import retrofit2.http.GET

interface LocationsService {
    companion object {
        private const val LOCATIONS = "locations.json"
    }

    @GET(LOCATIONS)
    fun getPlacemarks(): Single<Placemarks>
}