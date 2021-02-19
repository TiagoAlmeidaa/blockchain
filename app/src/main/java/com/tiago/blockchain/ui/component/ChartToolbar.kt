package com.tiago.blockchain.ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import com.tiago.blockchain.R
import com.tiago.blockchain.databinding.WidgetToolbarBinding
import com.tiago.blockchain.util.extensions.setVisibility
import com.tiago.blockchain.util.extensions.visible

class ChartToolbar(context: Context, attrs: AttributeSet) : CardView(context, attrs) {

    private val binding: WidgetToolbarBinding by lazy {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        WidgetToolbarBinding.inflate(inflater, this, true)
    }

    private var isGridEnabled = true

    init {
        background = null
        setGridIcon()
    }

    fun setup(titleId: Int, onBackPressed: () -> Unit) = with(binding) {
        toolbarTitleTextView.text = root.context.getString(titleId)
        toolbarBackImageView.setOnClickListener { onBackPressed() }
    }

    fun setupFilterOption(onFilterPressed: () -> Unit) = with(binding) {
        toolbarFilterImageView.visible()
        toolbarFilterImageView.setOnClickListener { onFilterPressed() }
    }

    fun setupGridOption(onGridPressed: (Boolean) -> Unit) = with(binding) {
        toolbarGridImageView.visible()
        toolbarGridImageView.setOnClickListener {
            isGridEnabled = !isGridEnabled
            setGridIcon()
            onGridPressed(isGridEnabled)
        }
    }

    private fun setGridIcon() = with(binding) {
        val drawableId = if (isGridEnabled) {
            R.drawable.icon_grid
        } else {
            R.drawable.icon_grid_off
        }
        toolbarGridImageView.setImageResource(drawableId)
    }

    fun setFilterVisibility(visible: Boolean) = with(binding){
        toolbarFilterImageView.setVisibility(visible)
    }

    fun setGridVisibility(visible: Boolean) = with(binding){
        toolbarGridImageView.setVisibility(visible)
    }

}