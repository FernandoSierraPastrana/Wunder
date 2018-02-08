package com.fernandosierra.wunder.presentation.custom

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity
import com.fernandosierra.wunder.presentation.Presenter
import dagger.android.AndroidInjection
import javax.inject.Inject

abstract class WActivity<P : Presenter<*>> : AppCompatActivity() {
    @Inject
    protected lateinit var presenter: P

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    @CallSuper
    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }
}