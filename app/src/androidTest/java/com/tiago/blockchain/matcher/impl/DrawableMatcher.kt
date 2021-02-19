package com.tiago.blockchain.matcher.impl

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.tiago.blockchain.util.extensions.toBitmap
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

class DrawableMatcher(private val resourceId: Int) : TypeSafeMatcher<View>() {

    override fun matchesSafely(item: View?): Boolean = with(item) {
        if (this == null || this !is AppCompatImageView)
            return false
        if (resourceId < 0)
            return drawable == null

        val expectedDrawable = ContextCompat.getDrawable(context, resourceId) ?: return false
        val expectedBitmap = expectedDrawable.toBitmap()

        return drawable.toBitmap().sameAs(expectedBitmap)
    }

    override fun describeTo(description: Description?) {
        description?.appendText("drawable with resource id: $resourceId")
    }

}