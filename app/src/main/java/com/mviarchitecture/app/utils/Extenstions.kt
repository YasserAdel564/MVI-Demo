package com.mviarchitecture.app.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.mviarchitecture.app.R


fun snackBar(message: String?, rootView: View) {
    val snackBar = Snackbar.make(rootView, message!!, Snackbar.LENGTH_LONG)
    val view = snackBar.view
    val textView = view.findViewById<View>(R.id.snackbar_text)
    textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
    snackBar.show()
}





