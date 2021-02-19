package com.tiago.blockchain.util.extensions

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable

fun Drawable.toBitmap(): Bitmap =
    Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888).apply {
        val canvas = Canvas(this).apply {
            setBounds(0, 0, width, height)
        }
        draw(canvas)
    }
