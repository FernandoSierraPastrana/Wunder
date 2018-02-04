package com.fernandosierra.wunder.data.repository

import com.fernandosierra.wunder.data.local.LocationsDataSource
import com.fernandosierra.wunder.data.mapper.PlacemarkMapper
import com.fernandosierra.wunder.data.network.LocationsService
import com.fernandosierra.wunder.domain.model.Placemark
import com.fernandosierra.wunder.presentation.util.REALM
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.rx2.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationsRepository @Inject constructor(private val service: LocationsService,
                                              private val dataSource: LocationsDataSource,
                                              private val mapper: PlacemarkMapper) {
    suspend fun update() {
        async {
            val placemarks = service.getPlacemarks().await()
            async(REALM) { dataSource.createOrUpdate(placemarks.items) }.await()
        }.await()
    }

    suspend fun getAll(): List<Placemark> =
            async(REALM) {
                val placemarks = dataSource.getAll()
                placemarks.map { mapper.transform(it) }
            }.await()
}