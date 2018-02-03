package com.fernandosierra.wunder.data.repository

import com.fernandosierra.wunder.data.local.LocationsDataSource
import com.fernandosierra.wunder.data.network.LocationsService
import com.fernandosierra.wunder.presentation.REALM
import kotlinx.coroutines.experimental.async
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationsRepository @Inject constructor(private val service: LocationsService, private val dataSource: LocationsDataSource) {

    suspend fun update() {
        val placemarks = service.getPlacemarks().await()
        async(REALM) { dataSource.createOrUpdate(placemarks.items) }.await()
    }
}