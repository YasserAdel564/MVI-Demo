package com.mviarchitecture.app.utils

import android.animation.AnimatorSet
import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.view.View
import androidx.core.view.animation.PathInterpolatorCompat
import com.google.android.material.snackbar.Snackbar
import com.mviarchitecture.app.R


fun snackBar(message: String?, rootView: View) {
    val snackBar = Snackbar.make(rootView, message!!, Snackbar.LENGTH_LONG)
    val view = snackBar.view
    val textView = view.findViewById<View>(R.id.snackbar_text)
    textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
    snackBar.show()
}

fun Context.flashAnimation(view: View) {
    val animator1 = ObjectAnimator.ofPropertyValuesHolder(
        view,
        PropertyValuesHolder.ofFloat(View.TRANSLATION_Z, 1000f, 0f)
    )
    animator1.duration = 3000
    animator1.interpolator = PathInterpolatorCompat.create(0.29f, 0.87f, 1f, 1f)
    val animator2 = ObjectAnimator.ofPropertyValuesHolder(
        view,
        PropertyValuesHolder.ofFloat(View.ALPHA, 0f, 1f)
    )
    animator2.duration = 2000
    animator2.interpolator = PathInterpolatorCompat.create(0.42f, 0f, 0.58f, 1f)
    val animator3 = ObjectAnimator.ofPropertyValuesHolder(
        view,
        PropertyValuesHolder.ofKeyframe(
            View.ROTATION,
            Keyframe.ofFloat(0f, -45f),
            Keyframe.ofFloat(0.7f, -45f),
            Keyframe.ofFloat(1f, 0f)
        )
    )
    animator3.duration = 0
    animator3.interpolator = PathInterpolatorCompat.create(0.42f, 0f, 0.58f, 1f)
    val animatorSet1 = AnimatorSet()
    animatorSet1.playTogether(animator1, animator2, animator3)
    val animatorSet2 = AnimatorSet()
    animatorSet2.playTogether(animatorSet1)
    animatorSet2.start()

}


