package com.fernandosierra.wunder.presentation

import android.app.Activity
import android.support.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.experimental.Job
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

open class Presenter<out V : ActivityView<*>>(protected val view: V) {
    private val pendingJobs = mutableListOf<Job>()
    protected val compositeDisposable = CompositeDisposable()

    protected fun manageJob(job: Job) {
        pendingJobs += job
        job.invokeOnCompletion {
            pendingJobs -= job
        }
    }

    @CallSuper
    open fun onPause() {
        compositeDisposable.clear()
        pendingJobs.map {
            it.cancel()
            pendingJobs.remove(it)
        }
    }
}

operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
    this.add(disposable)
}

operator fun CompositeDisposable.minusAssign(disposable: Disposable) {
    this.remove(disposable)
}
