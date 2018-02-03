package com.fernandosierra.wunder.data

import com.fernandosierra.wunder.BuildConfig
import com.fernandosierra.wunder.data.network.LocationsService
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun gson() = Gson()

    @Singleton
    @Provides
    fun retrofit(gson: Gson): Retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Singleton
    @Provides
    fun locationsService(retrofit: Retrofit): LocationsService = retrofit.create(LocationsService::class.java)
}