package com.fernandosierra.wunder.data.local

import com.fernandosierra.wunder.data.model.RPlacemark
import io.realm.Realm
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationsDataSource @Inject constructor() : DataSource<RPlacemark>() {
    override val primaryKey: String?
        get() = RPlacemark.VIN
    override val clazz: Class<RPlacemark>
        get() = RPlacemark::class.java

    override fun createOrUpdate(item: RPlacemark, realm: Realm?) {
        if (item.coordinates.size >= 2) {
            item.latitude = item.coordinates[0]
            item.longitude = item.coordinates[1]
        }
        super.createOrUpdate(item, realm)
    }
}