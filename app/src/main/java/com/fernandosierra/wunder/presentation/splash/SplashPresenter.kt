package com.fernandosierra.wunder.presentation.splash

import com.fernandosierra.wunder.domain.LocationsInteractor
import com.fernandosierra.wunder.presentation.Presenter
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import timber.log.Timber
import javax.inject.Inject

class SplashPresenter @Inject constructor(view: SplashView, private val locationsInteractor: LocationsInteractor)
    : Presenter<SplashView>(view) {

    fun updateLocations() {
        try {
            async(UI) {
                locationsInteractor.update()
                Timber.d("DONE")
            }
        } catch (t: Throwable) {
            Timber.e(t)
            //TODO Show Error
        }
    }
}