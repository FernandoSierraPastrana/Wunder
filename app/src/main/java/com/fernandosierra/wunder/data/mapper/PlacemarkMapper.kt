package com.fernandosierra.wunder.data.mapper

import com.fernandosierra.wunder.data.model.RPlacemark
import com.fernandosierra.wunder.domain.model.Placemark
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlacemarkMapper @Inject constructor() : Mapper<Placemark, RPlacemark> {

    suspend override fun transform(input: Placemark): RPlacemark {
        val output = RPlacemark(input.vin,
                input.name,
                input.address,
                input.fuel,
                input.engineType,
                input.exterior,
                input.interior)
        output.latitude = input.latitude
        output.longitude = input.longitude
        return output
    }

    suspend override fun transform(input: RPlacemark): Placemark =
            Placemark(input.vin,
                    input.name,
                    input.address,
                    input.fuel,
                    input.engineType,
                    input.exterior,
                    input.interior,
                    input.latitude,
                    input.longitude)
}