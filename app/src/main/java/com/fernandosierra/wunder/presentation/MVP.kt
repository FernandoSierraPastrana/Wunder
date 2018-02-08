package com.fernandosierra.wunder.presentation

import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableCompletableObserver
import kotlinx.coroutines.experimental.Job
import java.lang.ref.WeakReference
import kotlin.properties.Delegates

abstract class ActivityView<A : AppCompatActivity>(activity: A) {
    private val activityRef = WeakReference(activity)
    protected lateinit var observer: DisposableCompletableObserver
    open var state by Delegates.observable<ViewState>(ViewState.Created()) { _, _, new ->
        when (new) {
            is ViewState.Init -> onInit()
            is ViewState.Loading -> onLoading()
            is ViewState.Success<*> -> onSuccess()
            is ViewState.Error -> onError()
        }
    }

    protected fun view(block: (AppCompatActivity) -> Unit) {
        val activity = activityRef.get()
        if (activity != null) {
            block.invoke(activity)
        }
    }

    protected abstract fun onInit()

    protected abstract fun onLoading()

    protected abstract fun onSuccess()

    protected abstract fun onError()
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
    open fun onStop() {
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
