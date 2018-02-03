package com.fernandosierra.wunder.data.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey

open class RPlacemark(@PrimaryKey @SerializedName(VIN) var vin: String = "",
                      @SerializedName(NAME) var name: String = "",
                      @SerializedName(ADDRESS) var address: String = "",
                      @SerializedName(FUEL) var fuel: Int = 0,
                      @SerializedName(ENGINE) var engineType: String = "",
                      @SerializedName(EXTERIOR) var exterior: String = "",
                      @SerializedName(INTERIOR) var interior: String = "") : RealmObject() {
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    @SerializedName(COORDINATES)
    @Ignore
    var coordinates: MutableList<Double> = mutableListOf()

    companion object {
        const val VIN = "vin"
        private const val NAME = "name"
        private const val ADDRESS = "address"
        private const val FUEL = "fuel"
        private const val ENGINE = "engineType"
        private const val EXTERIOR = "exterior"
        private const val INTERIOR = "interior"
        private const val COORDINATES = "coordinates"
    }
}

class Placemarks(@SerializedName(PLACEMARKS) val items: List<RPlacemark>) {
    companion object {
        private const val PLACEMARKS = "placemarks"
    }
}