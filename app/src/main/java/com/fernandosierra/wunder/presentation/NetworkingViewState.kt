package com.fernandosierra.wunder.presentation

import android.support.annotation.StringRes
import io.reactivex.observers.DisposableCompletableObserver

sealed class NetworkingViewState {
    class Created : NetworkingViewState()
    class Init : NetworkingViewState()
    class Loading(val observer: DisposableCompletableObserver) : NetworkingViewState()
    class Success<out T>(val item: T) : NetworkingViewState()
    class Error(val error: Throwable, @StringRes val message: Int = 0) : NetworkingViewState()
}