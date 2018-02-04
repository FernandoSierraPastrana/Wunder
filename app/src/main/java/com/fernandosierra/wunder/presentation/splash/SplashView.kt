package com.fernandosierra.wunder.presentation.splash

import android.support.design.widget.Snackbar
import android.widget.FrameLayout
import com.fernandosierra.wunder.R
import com.fernandosierra.wunder.presentation.ActivityView
import com.fernandosierra.wunder.presentation.NetworkingViewState
import com.fernandosierra.wunder.presentation.NetworkingViewState.*
import com.fernandosierra.wunder.presentation.custom.LoaderView
import io.reactivex.observers.DisposableCompletableObserver
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject
import kotlin.properties.Delegates

class SplashView @Inject constructor(activity: SplashActivity) : ActivityView<SplashActivity>(activity) {
    private lateinit var container: FrameLayout
    private lateinit var loaderView: LoaderView
    private lateinit var observer: DisposableCompletableObserver
    var state by Delegates.observable<NetworkingViewState>(Created()) { _, _, new ->
        when (new) {
            is Init -> linkViews()
            is Loading -> onLoading()
            is Success<*> -> onSuccess()
            is Error -> onError()
        }
    }

    private fun linkViews() {
        view {
            container = it.frame_main_container
            loaderView = it.loader_splash
            loaderView.speed = 1.5f
        }
    }

    private fun onLoading() {
        loaderView.playAnimation()
        observer = (state as Loading).observer
    }

    private fun onSuccess() {
        loaderView.cancelAnimation { observer.onComplete() }
    }

    private fun onError() {
        loaderView.cancelAnimation {
            Snackbar.make(container, (state as Error).message, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.common_ok, { observer.onError((state as Error).error) })
                    .show()
        }
    }
}