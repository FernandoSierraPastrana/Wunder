package com.fernandosierra.wunder.data.mapper

import io.realm.RealmObject

interface Mapper<I, O : RealmObject> {
    suspend fun transform(input: I): O

    suspend fun transform(input: O): I
}