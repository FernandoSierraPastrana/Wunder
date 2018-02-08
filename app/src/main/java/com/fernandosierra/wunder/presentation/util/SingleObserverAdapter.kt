package com.fernandosierra.wunder.presentation.util

import android.support.annotation.CallSuper
import io.reactivex.observers.DisposableSingleObserver
import timber.log.Timber

abstract class SingleObserverAdapter<T> : DisposableSingleObserver<T>() {
    override fun onSuccess(t: T) {
        // Optional
    }

    @CallSuper
    override fun onError(e: Throwable) {
        Timber.e(e)
    }
}