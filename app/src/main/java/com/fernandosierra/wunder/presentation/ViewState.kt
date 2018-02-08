package com.fernandosierra.wunder.presentation

import android.support.annotation.StringRes
import io.reactivex.observers.DisposableCompletableObserver

open class ViewState {
    class Created : ViewState()
    class Init : ViewState()
    class Loading(val observer: DisposableCompletableObserver) : ViewState()
    class Success<out T>(val item: T) : ViewState()
    class Error(val error: Throwable, @StringRes val message: Int = 0) : ViewState()
}