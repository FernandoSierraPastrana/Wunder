package com.fernandosierra.wunder.presentation.util

import android.support.annotation.CallSuper
import io.reactivex.observers.DisposableCompletableObserver
import timber.log.Timber

abstract class CompletableObserverAdapter : DisposableCompletableObserver() {

    override fun onComplete() {
        // Optional
    }

    @CallSuper
    override fun onError(e: Throwable) {
        Timber.e(e)
    }
}