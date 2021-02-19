package com.tiago.blockchain.util.extensions

import android.view.View

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.setVisibility(visible: Boolean) {
    if (visible)
        visible()
    else
        gone()
}
