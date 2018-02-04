package com.fernandosierra.wunder.domain.interactor

import com.fernandosierra.wunder.data.repository.LocationsRepository
import com.fernandosierra.wunder.domain.model.Placemark
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationsInteractor @Inject constructor(private val locationsRepository: LocationsRepository) {

    suspend fun update() = locationsRepository.update()

    suspend fun getAll(): List<Placemark> = locationsRepository.getAll()
}