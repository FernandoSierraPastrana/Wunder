package com.fernandosierra.wunder.presentation.splash

import com.fernandosierra.wunder.R
import com.fernandosierra.wunder.domain.interactor.LocationsInteractor
import com.fernandosierra.wunder.presentation.NetworkingViewState.*
import com.fernandosierra.wunder.presentation.Presenter
import com.fernandosierra.wunder.presentation.minusAssign
import com.fernandosierra.wunder.presentation.navigation.Navigator
import com.fernandosierra.wunder.presentation.plusAssign
import com.fernandosierra.wunder.presentation.util.CompletableObserverAdapter
import io.reactivex.observers.DisposableCompletableObserver
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

class SplashPresenter @Inject constructor(view: SplashView,
                                          private val navigator: Navigator.SplashNavigator,
                                          private val locationsInteractor: LocationsInteractor) : Presenter<SplashView>(view) {

    fun init() {
        view.state = Init()
    }

    fun updateLocations() =
            manageJob(launch(UI) {
                view.state = Loading(createCompletable())
                try {
                    locationsInteractor.update()
                    view.state = Success(Unit)
                } catch (t: Throwable) {
                    view.state = Error(t, R.string.splash_error_message)
                }
            })

    private fun createCompletable(): DisposableCompletableObserver {
        val observer = object : CompletableObserverAdapter() {
            override fun onComplete() {
                compositeDisposable -= this
                navigator.goToMap()
            }

            override fun onError(e: Throwable) {
                super.onError(e)
                compositeDisposable -= this
                navigator.back()
            }
        }
        compositeDisposable += observer
        return observer
    }
}