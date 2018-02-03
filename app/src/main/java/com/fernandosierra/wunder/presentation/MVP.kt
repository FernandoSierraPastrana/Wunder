package com.fernandosierra.wunder.presentation

import android.app.Activity
import java.lang.ref.WeakReference

open class ActivityView<A : Activity>(activity: A) {
    private val activityRef = WeakReference(activity)

    protected fun view(block: (Activity) -> Unit) {
        val activity = activityRef.get()
        if (activity != null) {
            block.invoke(activity)
        }
    }
}

open class Presenter<out V : ActivityView<*>>(protected val view: V)
