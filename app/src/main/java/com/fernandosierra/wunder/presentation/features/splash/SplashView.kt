package com.fernandosierra.wunder.presentation.features.splash

import android.support.design.widget.Snackbar
import android.widget.FrameLayout
import com.fernandosierra.wunder.R
import com.fernandosierra.wunder.presentation.ActivityView
import com.fernandosierra.wunder.presentation.ViewState.Error
import com.fernandosierra.wunder.presentation.ViewState.Loading
import com.fernandosierra.wunder.presentation.custom.LoaderView
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

class SplashView @Inject constructor(activity: SplashActivity) : ActivityView<SplashActivity>(activity) {
    private lateinit var container: FrameLayout
    private lateinit var loaderView: LoaderView

    override fun onInit() =
            view {
                container = it.frame_main_container
                loaderView = it.loader_splash
                loaderView.speed = 1.5f
            }

    override fun onLoading() {
        loaderView.playAnimation()
        observer = (state as Loading).observer
    }

    override fun onSuccess() = loaderView.cancelAnimation { observer.onComplete() }

    override fun onError() =
            loaderView.cancelAnimation {
                val errorState = state as Error
                Snackbar.make(container, errorState.message, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.common_ok, { observer.onError(errorState.error) })
                        .show()
            }
}
