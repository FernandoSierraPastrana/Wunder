package com.fernandosierra.wunder.data.local

import io.realm.Realm
import io.realm.RealmObject

abstract class DataSource<T : RealmObject> {
    protected abstract val primaryKey: String?
    protected abstract val clazz: Class<T>

    open fun createOrUpdate(item: T, realm: Realm? = null) =
            if (realm == null) {
                Realm.getDefaultInstance().executeTransaction {
                    it.insertOrUpdate(item)
                }
            } else {
                realm.insertOrUpdate(item)
            }

    open suspend fun createOrUpdate(items: List<T>, realm: Realm = Realm.getDefaultInstance()) =
            realm.executeTransaction {
                items.map { item -> createOrUpdate(item, it) }
            }

    open suspend fun getAll(realm: Realm = Realm.getDefaultInstance()): List<T> {
        var results = listOf<T>()
        realm.executeTransaction {
            results = it.where(clazz).findAll()
        }
        return results
    }

    open suspend fun getByPrimaryKey(key: String, realm: Realm = Realm.getDefaultInstance()): T? {
        var result: T? = null
        if (primaryKey == null) {
            throw UnsupportedOperationException()
        }
        realm.executeTransaction {
            result = it.where(clazz)
                    .equalTo(primaryKey, key)
                    .findFirst()
        }
        return result
    }
}