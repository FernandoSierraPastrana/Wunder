package com.fernandosierra.wunder.presentation.navigation

import android.app.Activity
import android.content.Intent
import com.fernandosierra.wunder.presentation.map.MapActivity
import com.fernandosierra.wunder.presentation.splash.SplashActivity
import java.lang.ref.WeakReference
import javax.inject.Inject

sealed class Navigator<A : Activity>(activity: A) {
    private val activityRef = WeakReference(activity)

    protected fun view(block: (Activity) -> Unit) {
        val activity = activityRef.get()
        if (activity != null) {
            block.invoke(activity)
        }
    }

    fun back() = view { it.finish() }

    protected fun <T> launch(activity: Class<T>, block: ((Intent) -> Unit)? = null) =
            view {
                val intent = Intent(it, activity)
                block?.invoke(intent)
                it.startActivity(intent)
            }

    class SplashNavigator @Inject constructor(activity: SplashActivity) : Navigator<SplashActivity>(activity) {
        fun goToMap() {
            launch(MapActivity::class.java)
            back()
        }
    }
}