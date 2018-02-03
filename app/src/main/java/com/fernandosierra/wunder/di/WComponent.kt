package com.fernandosierra.wunder.di

import android.app.Application
import com.fernandosierra.wunder.data.DataModule
import com.fernandosierra.wunder.presentation.WApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, WContributor::class, DataModule::class])
interface WComponent {
    fun inject(app: WApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder

        fun build(): WComponent
    }
}