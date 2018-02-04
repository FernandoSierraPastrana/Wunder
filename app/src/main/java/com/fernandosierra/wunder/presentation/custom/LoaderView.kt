package com.fernandosierra.wunder.presentation.custom

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.util.AttributeSet
import com.airbnb.lottie.LottieAnimationView

class LoaderView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : LottieAnimationView(context, attrs, defStyleAttr) {

    fun cancelAnimation(onAnimationEnd: (() -> Unit)?) {
        if (onAnimationEnd == null) {
            cancelAnimation()
        } else {
            val animatorListener = object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    removeAnimatorListener(this)
                    onAnimationEnd.invoke()
                }
            }
            loop(false)
            addAnimatorListener(animatorListener)
        }
    }
}