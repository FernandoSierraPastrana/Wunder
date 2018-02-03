package com.fernandosierra.wunder.presentation

import android.os.Handler
import android.os.HandlerThread
import kotlinx.coroutines.experimental.android.HandlerContext

val REALM = HandlerContext(Handler(RealmContext.handlerThread.looper), RealmContext.NAME)

object RealmContext {
    internal const val NAME = "Realm"
    internal val handlerThread = HandlerThread(NAME)

    init {
        handlerThread.start()
    }
}