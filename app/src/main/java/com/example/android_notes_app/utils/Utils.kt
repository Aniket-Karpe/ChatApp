package com.example.android_notes_app.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

object Utils {
    fun showSnackbar(view: View?, message: String?) {
        val snackbar = Snackbar.make(view!!, message!!, Snackbar.LENGTH_SHORT)
        snackbar.show()
    }
}