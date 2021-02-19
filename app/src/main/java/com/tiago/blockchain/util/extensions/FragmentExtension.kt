package com.tiago.blockchain.util.extensions

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

fun Fragment.navigate(actionId: Int) {
    view?.let { nonNullView ->
        try {
            Navigation.findNavController(nonNullView).navigate(actionId)
        } catch (exception: Exception) {
            Log.e("FragmentExtension", "Unable to navigate, reason: ${exception.message}")
        }
    }
}