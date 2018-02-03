package com.fernandosierra.wunder.domain

import com.fernandosierra.wunder.data.repository.LocationsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationsInteractor @Inject constructor(private val locationsRepository: LocationsRepository) {

    suspend fun update() {
        locationsRepository.update()
    }
}